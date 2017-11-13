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
        command "cursorUp"
        command "cursorDown"
        command "cursorLeft"
        command "cursorRight"
        command "home"
        command "mute"
	}

	tiles(scale: 1) {
		standardTile("switch", "device.switch", width: 1, height: 1, canChangeIcon: true, decoration: "flat") {
        	state "off", label: '${name}', action: "switch.on", icon: "st.Entertainment.entertainment14", backgroundColor: "#ffffff"
            state "on", label: 'ON', action: "switch.off", icon: "st.Entertainment.entertainment14", backgroundColor: "#79b821"
		}
        
		standardTile("volumeUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"Volume", action:"volumeUp", icon:"st.thermostat.thermostat-up"
		} 

		standardTile("volumeDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"Volume", action:"volumeDown", icon:"st.thermostat.thermostat-down"
		} 

		standardTile("channelUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"Channel", action:"channelUp", icon:"st.thermostat.thermostat-up"
		}

		standardTile("channelDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"Channel", action:"channelDown", icon:"st.thermostat.thermostat-down"
		}
        
                
        standardTile("cursorUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"", action:"cursorUp", icon:"st.thermostat.thermostat-up"
		}
        
        standardTile("cursorDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"", action:"cursorDown", icon:"st.thermostat.thermostat-down"
		}
                
        standardTile("cursorLeft", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"", action:"cursorLeft", icon:"st.thermostat.thermostat-left"
		}
                
        standardTile("cursorRight", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"", action:"cursorRight", icon:"st.thermostat.thermostat-right"
		}
                        
        standardTile("cursorEnter", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"⏎", action:"cursorEnter", icon:""
		}
        
        standardTile("home", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"", action:"home", icon:"st.Home.home2"
		}
        
        standardTile("mute", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"Mute", action:"mute", icon:"st.custom.sonos.muted"
    	}
        
        standardTile("exit", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
			state "default", label:"", action:"exit", icon:"st.samsung.da.washer_ic_cancel"
    	}
        
        main "switch"
        details(["volumeUp", "switch", "channelUp", "volumeDown", "cursorUp", "channelDown", "cursorLeft", "cursorEnter", "cursorRight", "exit", "cursorDown", "home"])
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

    if (tvPort && tvPort != existingPort) {
    	updateDataValue("port", String.valueOf(tvPort))
	}
    if (tvPSK && tvPSK != existingPSK) {
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

def cursorUp() {

	log.debug "Executing 'cursorUp'"

	def rawcmd = "AAAAAQAAAAEAAAB0Aw=="
	sendremotecommand(rawcmd)
}

def cursorDown() {

	log.debug "Executing 'cursorDown'"

	def rawcmd = "AAAAAQAAAAEAAAB1Aw=="
	sendremotecommand(rawcmd)
}

def cursorLeft() {

	log.debug "Executing 'cursorLeft'"

	def rawcmd = "AAAAAQAAAAEAAAA0Aw=="
	sendremotecommand(rawcmd)
}

def cursorRight() {

	log.debug "Executing 'cursorRight'"

	def rawcmd = "AAAAAQAAAAEAAAAzAw=="
	sendremotecommand(rawcmd)
}

def cursorEnter() {

	log.debug "Executing 'cursorEnter'"

	def rawcmd = "AAAAAgAAAJcAAAAjAw=="
	sendremotecommand(rawcmd)
}

def home() {

	log.debug "Executing 'home'"
    
    def rawcmd = "AAAAAQAAAAEAAABgAw=="
    sendremotecommand(rawcmd)
}

def exit() {

	log.debug "Executing 'exit'"
    
    def rawcmd = "AAAAAQAAAAEAAABjAw=="
    sendremotecommand(rawcmd)
}

def mute() {

	log.debug "Executing 'mute'"
    
    def rawcmd = "AAAAAQAAAAEAAAAUAw=="
    sendremotecommand(rawcmd)
}

def WOLC() {

    //log.debug "WOLC mac: ${getDataValue("mac")}"
    log.debug "WOLC mac: ${device.deviceNetworkId}"
    
	def result = new physicalgraph.device.HubAction (
        "wake on lan ${device.deviceNetworkId}",
   		physicalgraph.device.Protocol.LAN,
   		null,
    	[secureCode: "111122223333"]
	)
	return result
}    