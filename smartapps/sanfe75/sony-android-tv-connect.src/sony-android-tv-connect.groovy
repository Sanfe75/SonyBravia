/**
 *  Sony Android TV (Connect)
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
definition(
    name: "Sony Android TV (Connect)",
    namespace: "Sanfe75",
    author: "Simone",
    description: "Connect your Sony Bravia TV",
    category: "SmartThings Labs",
    iconUrl: "https://upload.wikimedia.org/wikipedia/commons/a/a8/Sony_Bravia_logo.svg",
    iconX2Url: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a8/Sony_Bravia_logo.svg/320px-Sony_Bravia_logo.svg.png",
    iconX3Url: "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a8/Sony_Bravia_logo.svg/320px-Sony_Bravia_logo.svg.png",
    singleInstance: true
    )


preferences {
    
	page(name: "deviceDiscovery", title: "Sony Device Discovery", content: "deviceDiscovery")
    page(name: "deviceSetup", title: "Sony Device Setup", content: "deviceSetup")
    
}

def searchTarget() {
	return "urn:schemas-upnp-org:device:MediaRenderer:1"
}

def deviceDiscovery() {
	def options = [:]
	def devices = getVerifiedDevices()
	devices.each {
		def value = it.value.name ?: "UPnP Device ${it.value.ssdpUSN.split(':')[1][-3..-1]}"
		def key = it.value.mac
		options["${key}"] = value
	}

	ssdpSubscribe()

	ssdpDiscover()
	verifyDevices()
    
    state.index = 0

	return dynamicPage(name: "deviceDiscovery", title: "Discovery Started!", nextPage: "deviceSetup", refreshInterval: 5, install: false, uninstall: true) {
		section("Please wait while we discover your Bravia TV. Discovery can take five minutes or more, so sit back and relax! Select your device below once discovered.") {
			input "selectedDevices", "enum", required: true, title: "Select Devices (${options.size() ?: 0} found)", multiple: true, options: options
		}
	}
}

def deviceSetup() {

	log.debug "deviceSetup state.index: ${state.index}"
	if (state.index > 0) {
        putInDevice([tvName: tvName, tvIcon: tvIcon, tvPort: tvPort, tvPSK: tvPSK])
    }
    
	state.index = state.index + 1
    def isLast = state.index >= selectedDevices.size()
    def nextPage = isLast? "" : "deviceSetup"
    def mac = selectedDevices[state.index - 1]
    def device = getDevices().find {it.value.mac == mac}
    def child = getChildDevice(mac)
    def newDevice = (!getChildDevice(mac)) ? true : false

	return dynamicPage(name: "deviceSetup", nextPage: nextPage, install: isLast, uninstall: true) {
		section("Please input the Device info for ${device.value.name}") {
			if (newDevice) {
            	input(name: "tvName", type: "text", title: "Name for the Sony TV Device", defaultValue: device.value.name, required: true)
                input(name: "tvIcon", type: "icon", title: "Choose an icon for the TV", required: true)
            }
        	input(name: "tvPort", type: "number", range: "0..9999", title: "Port", defaultValue: device.value.tvPort, required: true)
			input(name: "tvPSK", type: "password", title: "PSK Passphrase set on your TV", description: "Enter passphrase", required: true)
		}
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
    
    selectedDevices.each {
    	def child = getChildDevice(it)
        def mac = it
		if (child) {
        	def selectedDevice = getDevices().find { mac == it.value.mac}
			child.update(selectedDevice.value.tvIcon, selectedDevice.value.tvPort, selectedDevice.value.tvPSK)
		}
    }
}

def initialize() {

	unschedule()

	ssdpSubscribe()

    putInDevice([tvName: tvName, tvIcon: tvIcon, tvPort: tvPort, tvPSK: tvPSK])

	if (selectedDevices) {
		addDevices()
	}

	runEvery5Minutes("ssdpDiscover")
    state.index = 0
}

void ssdpDiscover() {
	sendHubCommand(new physicalgraph.device.HubAction("lan discovery ${searchTarget()}", physicalgraph.device.Protocol.LAN))
}

void ssdpSubscribe() {
	subscribe(location, "ssdpTerm.${searchTarget()}", ssdpHandler)
}

Map verifiedDevices() {
	def devices = getVerifiedDevices()
	def map = [:]
	devices.each {
		def value = it.value.name ?: "UPnP Device ${it.value.ssdpUSN.split(':')[1][-3..-1]}"
		def key = it.value.mac
		map["${key}"] = value
	}
	map
}

void verifyDevices() {
	def devices = getDevices().findAll { it?.value?.verified != true }
	devices.each {
		int port = convertHexToInt(it.value.deviceAddress)
		String ip = convertHexToIP(it.value.networkAddress)
		String host = "${ip}:${port}"
		sendHubCommand(new physicalgraph.device.HubAction("""GET ${it.value.ssdpPath} HTTP/1.1\r\nHOST: $host\r\n\r\n""", physicalgraph.device.Protocol.LAN, host, [callback: deviceDescriptionHandler]))
	}
}

def getVerifiedDevices() {
	getDevices().findAll{ it.value.verified == true }
}

def getDevices() {
	if (!state.devices) {
		state.devices = [:]
	}
	state.devices
}

def putInDevice(newValues){
	
    def devices = getDevices()
    def device = devices.find {it.value.mac == selectedDevices[state.index - 1]}
    device.value << newValues
    //log.debug "putInDevice device.value: ${device.value}"
}

def addDevices() {
	def devices = getDevices()

	selectedDevices.each { dni ->
		def selectedDevice = devices.find { it.value.mac == dni }
		def d
		if (selectedDevice) {
			d = getChildDevices()?.find {
				it.deviceNetworkId == selectedDevice.value.mac
			}
		}

		if (!d) {

			addChildDevice("Sanfe75", "Sony TV", selectedDevice.value.mac, selectedDevice?.value.hub, [
				"label": selectedDevice.value?.tvName ?: selectedDevice.value.name,
				"data": [
					"mac": selectedDevice.value.mac,
					"ip": convertHexToIP(selectedDevice.value.networkAddress),
                    "port": selectedDevice.value.tvPort,
                    "tvIcon": selectedDevice.value.tvIcon,
                    "tvPSK": selectedDevice.value.tvPSK
				]
			])
		}
	}
}

def ssdpHandler(evt) {

	def description = evt.description
	def hub = evt?.hubId
    def parsedEvent = parseLanMessage(description)
	parsedEvent << ["hub":hub]
    
    //log.debug "ssdpHandler parsedEvent: ${parsedEvent}"

	def devices = getDevices()
	String ssdpUSN = parsedEvent.ssdpUSN.toString()
	if (devices."${ssdpUSN}") {
		def d = devices."${ssdpUSN}"
		if (d.networkAddress != parsedEvent.networkAddress || d.deviceAddress != parsedEvent.deviceAddress) {
			d.networkAddress = parsedEvent.networkAddress
			d.deviceAddress = parsedEvent.deviceAddress
			def child = getChildDevice(parsedEvent.mac)
			if (child) {
				child.sync(convertHexToIP(parsedEvent.networkAddress))
			}
		}
	} else {
		devices << ["${ssdpUSN}": parsedEvent]
	}
}

void deviceDescriptionHandler(physicalgraph.device.HubResponse hubResponse) {
	def body = hubResponse.xml
    body.children().each {
    	log.debug "deviceDescriptionHandler children: ${it.name()} --> ${it.text()}"
    }  
    body.device.children().each {
    	log.debug "deviceDescriptionHandler device.children: ${it.name()} --> ${it.text()}"
    }    
    
	def devices = getDevices()
	def device = devices.find { it?.key?.contains(body?.device?.UDN?.text()) }
	if (device) {
		device.value << [name: body?.device?.friendlyName?.text(), model:body?.device?.modelName?.text(), serialNumber:body?.device?.serialNum?.text(), verified: true]
	}
}

private Integer convertHexToInt(hex) {
	Integer.parseInt(hex,16)
}

private String convertHexToIP(hex) {
	[convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}