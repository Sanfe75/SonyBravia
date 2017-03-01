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
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Sony TV", namespace: "Sanfe75", author: "Simone") {
		capability "Switch"
		capability "TV"
	}


	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
        	state "off", label: '${name}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
            state "on", label: 'ON', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
		}
	}

	preferences {

		/*input name: "ipadd1", type: "number", range: "0..254", required: true, title: "Ip address part 1", displayDuringSetup: true
		input name: "ipadd2", type: "number", range: "0..254", required: true, title: "Ip address part 2", displayDuringSetup: true
		input name: "ipadd3", type: "number", range: "0..254", required: true, title: "Ip address part 3", displayDuringSetup: true
		input name: "ipadd4", type: "number", range: "0..254", required: true, title: "Ip address part 4", displayDuringSetup: true
		input name: "tv_port", type: "number", range: "0..9999", required: true, title: "Port Usually: 80", displayDuringSetup: true*/
		input name: "tv_psk", type: "text", title: "PSK Passphrase Set on your TV", description: "Enter passphrase", required: true, displayDuringSetup: true
	
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'switch' attribute
	// TODO: handle 'volume' attribute
	// TODO: handle 'channel' attribute
	// TODO: handle 'power' attribute
	// TODO: handle 'picture' attribute
	// TODO: handle 'sound' attribute
	// TODO: handle 'movieMode' attribute

}

// handle commands

def sendremotecommand(){
	log.debug "Sending Button: ${state.button} ${state.remotecommand}"
    def rawcmd = "${state.remotecommand}"
    def sonycmd = new physicalgraph.device.HubSoapAction(
            path:    '/sony/IRCC',
            urn:     "urn:schemas-sony-com:service:IRCC:1",
            action:  "X_SendIRCC",
            body:    ["IRCCCode":rawcmd],
            headers: [Host:"${state.tv_ip}:${tv_port}", 'X-Auth-PSK':"${tv_psk}"]
     )
     sendHubCommand(sonycmd)
     log.debug( "hubAction = ${sonycmd}" )
}

def on() {
	log.debug "Executing 'on'"
	
    def json = "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"params\":[{\"status\":true}],\"id\":102}"
    def result = sendJsonRpcCommand(json)
}

def off() {
	log.debug "Executing 'off'"
	
    def json = "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"params\":[{\"status\":false}],\"id\":102}"
    def result = sendJsonRpcCommand(json)
}

def volumeUp() {
	log.debug "Executing 'volumeUp'"
    
	state.remotecommand = "AAAAAQAAAAEAAAASAw=="
	state.button = "volumeup"
	sendremotecommand()
}

def volumeDown() {
	log.debug "Executing 'volumeDown'"

	state.remotecommand = "AAAAAQAAAAEAAAATAw=="
	state.button = "volumedown"
	sendremotecommand()
}

def channelUp() {
	log.debug "Executing 'channelUp'"

	state.remotecommand = "AAAAAQAAAAEAAAAQAw=="
	state.button = "ChannelUp"
	sendremotecommand()
}

def channelDown() {
	log.debug "Executing 'channelDown'"

	state.remotecommand = "AAAAAQAAAAEAAAARAw=="
	state.button = "ChannelDown"
	sendremotecommand()
}