/**
 *  Sony TV
 *
 *  Copyright 2017 Simone
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Based on Steve Bratt's code:
 *   https://gist.github.com/steveAbratt/43133bf9011febf6437a662eb5998ec8
 *
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Sony TV", namespace: "Sanfe75", author: "Simone") {
		capability "Actuator"
        capability "Switch"
		capability "TV"
        
        command "volumeUp"
        command "volumeDown"
        command "channelUp"
        command "channelDown"
	}

	tiles(scale: 2) {
		standardTile("switch", "device.switch", width: 4, height: 4, canChangeIcon: true) {
        	state "off", label: '${name}', action: "switch.on", icon: "st.Entertainment.entertainment14", backgroundColor: "#ffffff"
            state "on", label: 'ON', action: "switch.off", icon: "st.Entertainment.entertainment14", backgroundColor: "#79b821"
		}
        /*
        def tiles = ["volumeUp", "volumeDown", "channelUp", "channelDown"]
        tiles.each {
        	standardTile(it, "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:it, action:it, icon:""
            }
		}*/
        
		standardTile("volumeUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"volumeUp", action:"volumeUp", icon:"st.thermostat.thermostat-up"
		} 

		standardTile("volumeDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"volumeDown", action:"volumeDown", icon:"st.thermostat.thermostat-down"
		} 

		standardTile("channelUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"channelUp", action:"channelUp", icon:""
		}

		standardTile("channelDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"channelDown", action:"channelDown", icon:""
		}
        
        main "switch"
        details(["volumeUp", "switch", "channelUp", "volumeDown", "channelDown"])
	}
}

def installed() {

	initialize()
}

 def updated() {

	//unsubscribe()
    unschedule()
	initialize()
}

def initialize() {

    runEvery1Minute("pushPowerUpdate")
	//subscribeAction("/sony/system")
}

// parse events into attributes
def parse(description) {

    def msg = parseLanMessage(description)
    log.debug "parse Message.json '${msg.json}'"
    if (msg.json?.id == 2) {
        def tv = (msg.json.result[0]?.status == "active") ? "on" : "off"
        if (tv != state.tv) {
        	log.debug "parse change state: ${tv}"
        	state.tv = tv
        	sendEvent(name: "switch", value: tv)
        }
    }
	/*
    def volumeEvent = createEvent(name: "volume", value: "on")
    def channelEvent = createEvent(name: "channel", value: "on")
	def powerEvent = createEvent(name: "power", value: "on")
	def pictureEvent = createEvent(name: "picture", value: "on")
	def soundEvent = createEvent(name: "sound", value: "on")
	def movieModeEvent = createEvent(name: "movieMode", value: "on")
	*/
}

def update(tvPort, tvPSK){

    def existingPort = getDataValue("port")
    def existingPSK = getDataValue("tvPSK")

    if (tvPort && tvPort != exixtingPort) {
    	updateDataValue("port", String.valueOf(tvPort))
	}
    if (tvPSK && tvPSK != exixtingPSK) {
    	updateDataValue("tvPSK", tvPSK)
	}
}

def sync(ip) {
	def existingIp = getDataValue("ip")
	if (ip && ip != existingIp) {
		updateDataValue("ip", ip)
	}
}

private pushPowerUpdate() {

    def powerJson = "{\"id\":2,\"method\":\"getPowerStatus\",\"version\":\"1.0\",\"params\":[]}"
    def result = sendJsonRpcCommand(powerJson)
    /*def modeJson = "{\"id\":3,\"method\":\"getDeviceMode\",\"version\":\"1.0\",\"params\":[\"game\"]}"
    def mode = sendJsonRpcCommand(modeJson)*/
}
/*
private subscribeAction(path, callbackPath="") {


    //def address = getCallBackAddress()
    //def ip = getHostAddress()

    def result = new physicalgraph.device.HubAction(
        method: "SUBSCRIBE",
        path: path,
        headers: [
            HOST: getDataValue("ip"),
            //CALLBACK: "<http://${address}/notify${callbackPath}>",
            CALLBACK: "<http://192.168.1.166/notify${callbackPath}>",
            NT: "upnp:event",
            TIMEOUT: "Second-28800"
        ]
    )

    log.trace "SUBSCRIBE $path"

    return result
}*/

// handle commands

private sendremotecommand(rawcmd){

	def sonycmd = new physicalgraph.device.HubSoapAction(
            path:    '/sony/IRCC',
            urn:     "urn:schemas-sony-com:service:IRCC:1",
            action:  "X_SendIRCC",
            body:    ["IRCCCode":rawcmd],
            headers: [Host:"${getDataValue("ip")}:${getDataValue("port")}", 'X-Auth-PSK':"${getDataValue("tvPSK")}"]
     )
     sendHubCommand(sonycmd)
}

private sendJsonRpcCommand(json) {

  def result = new physicalgraph.device.HubAction(
    method: 'POST',
    path: '/sony/system',
    body: json,
    headers: ['HOST':"${getDataValue("ip")}:${getDataValue("port")}", 'Content-Type': "application/json", 'X-Auth-PSK':"${getDataValue("tvPSK")}"]
  )
  sendHubCommand(result)
}

def on() {

	WOLC()
    def json = "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"params\":[{\"status\":true}],\"id\":102}"
    def result = sendJsonRpcCommand(json)
    sendEvent(name: "switch", value: "on")
    state.tv = "on"
}

def off() {

	def json = "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"params\":[{\"status\":false}],\"id\":102}"
    def result = sendJsonRpcCommand(json)
    sendEvent(name: "switch", value: "off")
    state.tv = "off"
}

def volumeUp() {

	log.debug "Executing 'volumeUp'"
    
	def rawcmd = "AAAAAQAAAAEAAAASAw=="
	sendremotecommand(rawcmd)
}

def volumeDown() {

	log.debug "Executing 'volumeDown'"

	def rawcmd = "AAAAAQAAAAEAAAATAw=="
	sendremotecommand(rawcmd)
}

def channelUp() {

	log.debug "Executing 'channelUp'"

	def rawcmd = "AAAAAQAAAAEAAAAQAw=="
	sendremotecommand(rawcmd)
}

def channelDown() {

	log.debug "Executing 'channelDown'"

	def rawcmd = "AAAAAQAAAAEAAAARAw=="
	sendremotecommand(rawcmd)
}

def WOLC() {

    //log.debug "WOLC mac: ${getDataValue("mac")}"
    log.debug "WOLC mac: ${device.deviceNetworkId}"
    
	def result = new physicalgraph.device.HubAction (
  	  	//"wake on lan ${getDataValue("mac")}",
        "wake on lan ${device.deviceNetworkId}",
   		physicalgraph.device.Protocol.LAN,
   		null,
    	[secureCode: "111122223333"]
	)
	return result
}    