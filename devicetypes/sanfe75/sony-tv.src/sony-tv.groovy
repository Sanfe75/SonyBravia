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

def getDefaultTheme() {
    def userDefaultThemeMap = [:]
    //v1.2 START
    //ICONS
    userDefaultThemeMap.themeName = "Default"
    userDefaultThemeMap.iconStop = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/stop-icon.png"
    userDefaultThemeMap.iconShutdown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/power-icon.png"
    userDefaultThemeMap.iconUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/up-icon.png"
    userDefaultThemeMap.iconDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/down-icon.png"
    userDefaultThemeMap.iconLeft = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/left-icon.png"
    userDefaultThemeMap.iconRight = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/right-icon.png"
    userDefaultThemeMap.iconBack = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/back-icon.png"
    userDefaultThemeMap.iconInfo = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/info-icon.png"
    userDefaultThemeMap.iconSkipFwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/small-fwd-icon.png"
    userDefaultThemeMap.iconSkipRwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/small-rwd-icon.png"
    userDefaultThemeMap.iconNext = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/next-icon.png"
    userDefaultThemeMap.iconPrevious = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/prev-icon.png"
    userDefaultThemeMap.iconMenu = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/menu-icon.png"
    userDefaultThemeMap.iconHome = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/home-icon.png"
    userDefaultThemeMap.iconPgUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/pg-up-icon.png"
    userDefaultThemeMap.iconPgDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/pg-down-icon.png"
    //COLOURS
    userDefaultThemeMap.colMainWaiting = "#ffffff"     //White
    userDefaultThemeMap.colMainStartup = "#90d2a7"     //Light Green
    userDefaultThemeMap.colMainPlaying = "#79b821"     //Green
    userDefaultThemeMap.colMainStopped = "#153591"     //Blue
    userDefaultThemeMap.colMainPaused = "#e86d13"      //Orange
    userDefaultThemeMap.colMainShutdown = "#e84e4e"    //Red
    //v1.2 END
    //v1.3 START
    //v1.3 END
    //Return
    return userDefaultThemeMap
}

def getUserPref(pref) {
    def prefsMap = [:]
    //Main Icon
    prefsMap.iconMain = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/main-icon.png"
    //Select Colour
    prefsMap.colSelectActive = "#22a3ec"    //Blue
    prefsMap.colSelectInactive = "#ffffff"  //White
    //DECORATION
    prefsMap.decPush = "ring"
    prefsMap.decStop = "ring"
    prefsMap.decShutdown = "flat"
    prefsMap.decUp = "flat"
    prefsMap.decDown = "flat"
    prefsMap.decLeft = "flat"
    prefsMap.decRight = "flat"
    prefsMap.decBack = "flat"
    prefsMap.decInfo = "ring"
    prefsMap.decSkipF = "flat"
    prefsMap.decSkipB = "flat"
    prefsMap.decNext = "flat"
    prefsMap.decPrev = "flat"
    prefsMap.decMenu = "flat"
    prefsMap.decHome = "flat"
    prefsMap.decPup = "flat"
    prefsMap.decPdown = "flat"
    //CATEGORY SETTINGS
    prefsMap.movieLabels = "cinema, movie, film"
    prefsMap.sportLabels = "sport"
    prefsMap.tvLabels = "bbc, itv, channel, sky, amc, fox"
    prefsMap.minMovieRuntime = 4200
    return prefsMap[pref]
}

def getUserTheme(index){
    return getUserTheme(inputTheme ?: state?.theme ?: "default", index)
}

def getUserTheme(theme, index){
    switch (theme){
        case "glyphs":
            if (!state?.glyphsTheme){
                return glyphsTheme[index]
            }
            return state.glyphsTheme[index]
            break;
        case "mayssam":
            if (!state?.mayssamTheme){
                return mayssamTheme[index]
            }
            return state.mayssamTheme[index]
            break;
        default:
            if (!state?.defaultTheme){
                return defaultTheme[index]
            }
            return state.defaultTheme[index]
    }
}

metadata {
	definition (name: "Sony TV", namespace: "Sanfe75", author: "Simone") {
		capability "Switch"
        capability "Polling"
        capability "Refresh"
        capability "Media Controller"
	    capability "Music Player"
        capability "Switch Level"
	    capability "TV"
    
        command "volumeUp"
        command "volumeDown"
        command "channelUp"
        command "channelDown"
        command "cursorUp"
        command "cursorDown"
        command "cursorLeft"
        command "cursorRight"
        command "cursorReturn"
        command "cursorEnter"
        command "confirm"
        command "actionMenu"
        command "forward"
        command "play"
        command "rewind"
        command "prev"
        command "stop"
        command "next"
        command "rec"
        command "pause"
        command "netflix"
        command "sceneSelect"
        command "mode3D"
        command "home"
        command "exit"
        command "mute"
        command "red"
        command "green"
        command "yellow"
        command "blue"
        command "num1"
        command "num2"
        command "num3"
        command "num4"
        command "num5"
        command "num6"
        command "num7"
        command "num8"
        command "num9"
        command "num0"
        command "num11"
        command "num12"
        command "picOff"    
        command "tvSource"
        command "hdmi1"
        command "hdmi2"
        command "hdmi3"
        command "hdmi4"
        command "WOLC"
        
        /*
        command "digital"
        command "gguide"
        command "epg"
        command "favorites"
        command "display"
        command "tv_Radio"
        command "theater"
        command "SEN"
        command "internetWidgets"
        command "internetVideo"
        command "iManual"
        command "audio"
        command "wide"
        command "jump"
        command "subTitle"
        command "closedCaption"
        command "enter"
        command "DOT"
        command "analog"
        command "teletext"
        command "analog2"
        command "AD"
        command "analogg"
        command "BS"
        command "CS"
        command "BSCS"
        command "Ddata"
        command "PAP"
        command "myEPG"
        command "programDescription"
        command "writeChapter"
        command "trackID"
        command "tenKey"
        command "appliCast"
        command "acTVila"
        command "deleteVideo"
        command "photoFrame"
        command "tvPause"
        command "keyPad"
        command "media"
        command "eject"
        command "flashPlus"
        command "flashMinus"
        command "topMenu"
        command "rakurakuStart"
        command "oneTouchTimeRec"
        command "oneTouchView"
        command "oneTouchRec"
        command "oneTouchStop"
        command "DUX"
        command "footballMode"
        command "syncMenu"
        */
    }

    tiles(scale: 2) {
        multiAttributeTile(name: "switch", type:"generic", width:6, height:2) {
            tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'On', action:"off", icon:"st.Electronics.electronics18", backgroundColor:"#00A0DC", nextState:"turningOff"
                attributeState "off", label:'Off', action:"on", icon: "st.Electronics.electronics18", backgroundColor:"#ffffff", nextState:"turningOn"
                attributeState "turningOn", label:'Turning On', icon: "st.Electronics.electronics18", backgroundColor:"#79b821", nextState:"turningOff"
                attributeState "turningOff", label:'Turning Off', icon: "st.Electronics.electronics18", backgroundColor:"#ffffff", nextState:"turningOn"
            }
            tileAttribute("device.level", key: "VALUE_CONTROL") {
                attributeState "VALUE_UP", action: "volumeUp"
        	    attributeState "VALUE_DOWN",action: "volumeDown"
            }
            tileAttribute("device.switch", key: "SECONDARY_CONTROL") {
                attributeState "default", label:"", action:"mute", icon:"https://user-images.githubusercontent.com/8125308/33038669-0ab29848-cdfb-11e7-9b34-7432768a4bf1.png"
    	    }
//            tileAttribute ("device.level", key: "SLIDER_CONTROL") {
//                attributeState "level", label:'${currentValue}', action:"setVolume"
//		    }
	    }
    
        standardTile("power", "device.switch", width: 2, height: 2, canChangeIcon: false) {
            state "off", label: '', action: "on", icon:"st.thermostat.heating-cooling-off", backgroundColor: "#ffffff"//, nextState: "on"
		    state "on", label: '', action: "off", icon:"st.thermostat.heating-cooling-off", backgroundColor: "#79b821"//, nextState: "off"
        }
    
        standardTile("on", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"On", action:"on", icon:""
        }
        standardTile("off", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"off", action:"off", icon:""
        }
        
        standardTile("digital", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Digital", action:"digital", icon:""
        }
    
        standardTile("picOff", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Pic Off", action:"picOff", icon:"https://user-images.githubusercontent.com/8125308/32901483-853dca4a-cab5-11e7-9f17-bd51ebb28ab4.png"
        }

        standardTile("refresh", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }
   
        standardTile("tvSource", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Source", action:"tvSource", icon:""
        }

        standardTile("WOLC", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Wake on Lan", action:"WOLC", icon:""
        }

        standardTile("hdmi1", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 1", action:"hdmi1", icon:"https://user-images.githubusercontent.com/8125308/32800186-4c21fee2-c93f-11e7-9b56-6489145a8777.png"
        }

       standardTile("hdmi2", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 2", action:"hdmi2", icon:"https://user-images.githubusercontent.com/8125308/32800186-4c21fee2-c93f-11e7-9b56-6489145a8777.png"
        }

       standardTile("hdmi3", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 3", action:"hdmi3", icon:"https://user-images.githubusercontent.com/8125308/32800186-4c21fee2-c93f-11e7-9b56-6489145a8777.png"
        }
    
        standardTile("hdmi4", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 4", action:"hdmi4", icon:"https://user-images.githubusercontent.com/8125308/32800186-4c21fee2-c93f-11e7-9b56-6489145a8777.png"
        } 

        standardTile("netflix", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Netflix", action:"netflix", icon:"https://user-images.githubusercontent.com/8125308/32901185-e0197884-cab4-11e7-8da6-2b24b32711b9.png"
        }
    
        standardTile("home", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Home", action:"home", icon:"https://user-images.githubusercontent.com/8125308/32844712-bbfc801c-c9e8-11e7-93bd-8bc9592b67d6.png"
        }

        standardTile("mute", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Mute", action:"mute", icon:"https://user-images.githubusercontent.com/8125308/32844483-1e27bab4-c9e8-11e7-88ec-af4e521c4baa.png"
        }

        standardTile("gguide", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"gguide", action:"gguide", icon:""
	    }
    
        standardTile("epg", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Guide", action:"epg", icon:""
        }

        standardTile("favorites", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"favorites", action:"favorites", icon:""
        }

        standardTile("display", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"", action:"display", icon:"${getUserTheme('default', 'iconInfo')}"
        }

        standardTile("actionMenu", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Menu", action:"actionMenu", icon:"https://user-images.githubusercontent.com/8125308/32844133-3a43d0bc-c9e7-11e7-9bb2-2d763651f9b7.png"
        }

        standardTile("confirm", "device.switch", inactiveLabel: false, height: 2, width: 2, decoration: "flat") {
            state "default", label:"Select", action:"confirm", icon:"https://user-images.githubusercontent.com/8125308/32848217-83fdb07e-c9f1-11e7-8e20-bd388b6c964a.png"
        }

        standardTile("cursorUp", "device.switch", inactiveLabel: false, height: 1, width: 2, decoration: "flat") {
            state "default", label:"", action:"cursorUp", icon:"${getUserTheme('default','iconUp')}"
        }

        standardTile("cursorReturn", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Return", action:"cursorReturn", icon:"https://user-images.githubusercontent.com/8125308/32848690-b5620b32-c9f2-11e7-8846-b5da0d80aebd.png"
        } 

        standardTile("red", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"red", action:"red", icon:"", backgroundColor: "#ff0000"
        }

        standardTile("green", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Freeview", action:"green", icon:"", backgroundColor: "#008000"
        }

        standardTile("yellow", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"yellow", action:"yellow", icon:"", backgroundColor: "#ffff00"
        }

        standardTile("cursorDown", "device.switch", inactiveLabel: false, height: 1, width: 2, decoration: "flat") {
            state "default", label:"", action:"cursorDown", icon:"${getUserTheme('default','iconDown')}"
        }

        standardTile("cursorRight", "device.switch", inactiveLabel: false, height: 2, width: 1, decoration: "flat") {
            state "default", label:"", action:"cursorRight", icon:"${getUserTheme('default','iconRight')}"
        }

        standardTile("cursorLeft", "device.switch", inactiveLabel: false, height: 2, width: 1, decoration: "flat") {
            state "default", label:"", action:"cursorLeft", icon:"${getUserTheme('default','iconLeft')}"
        }

        standardTile("blue", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"blue", action:"blue", icon:"", backgroundColor: "#0000ff"
        }

        standardTile("num1", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num1", action:"num1", icon:""
        }

        standardTile("num2", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num2", action:"num2", icon:""
        }

        standardTile("num3", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num3", action:"num3", icon:""
        }

        standardTile("num4", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num4", action:"num4", icon:""
        }

        standardTile("num5", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num5", action:"num5", icon:""
        }

        standardTile("num6", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num6", action:"num6", icon:""
        }

        standardTile("num7", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num7", action:"num7", icon:""
        }

        standardTile("num8", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num8", action:"num8", icon:""
        }

        standardTile("num9", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num9", action:"num9", icon:""
        }

        standardTile("num0", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num0", action:"num0", icon:""
        }

        standardTile("num11", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num11", action:"num11", icon:""
        }

        standardTile("num12", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num12", action:"num12", icon:""
        }

        standardTile("volumeUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Vol Up", action:"volumeUp", icon:"${getUserTheme('default','iconUp')}"
        }

        standardTile("volumeDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Vol Down", action:"volumeDown", icon:"${getUserTheme('default','iconDown')}"
        }

        standardTile("channelUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Channel Up", action:"channelUp", icon:"${getUserTheme('default','iconUp')}"
        }

        standardTile("channelDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Channel Down", action:"channelDown", icon:"${getUserTheme('default','iconDown')}"
        }

        standardTile("subTitle", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"SubTitle", action:"subTitle", icon:""
        }

        standardTile("closedCaption", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"ClosedCaption", action:"closedCaption", icon:"https://user-images.githubusercontent.com/8125308/32843990-d42ddcb4-c9e6-11e7-96c2-c9fd328ab250.png"
        }

        standardTile("cursorEnter", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Enter", action:"cursorEnter", icon:""
        }

        standardTile("DOT", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"DOT", action:"DOT", icon:""
        }

        standardTile("analog", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Analog", action:"analog", icon:""
        }

        standardTile("teletext", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Teletext", action:"teletext", icon:""
        }

        standardTile("exit", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Exit", action:"exit", icon:"https://user-images.githubusercontent.com/8125308/32848616-851b40e2-c9f2-11e7-8abf-59fbb245e2f8.png"
        }

        standardTile("mode3D", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"3D", action:"mode3D", icon:"https://user-images.githubusercontent.com/8125308/32901158-cf46b6fc-cab4-11e7-9adc-d7a20a8686e9.png"
        }

        standardTile("iManual", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Help", action:"iManual", icon:""
        }

        standardTile("audio", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Audio", action:"audio", icon:""
        }

        standardTile("wide", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Aspect Ratio", action:"wide", icon:""
        }

        standardTile("jump", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Jump", action:"jump", icon:""
        }

        standardTile("PAP", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"PIP", action:"PAP", icon:""
        }

        standardTile("photoFrame", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Recorded", action:"photoFrame", icon:""
        }

        standardTile("media", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Streaming", action:"media", icon:""
        }

        standardTile("syncMenu", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Sync Menu", action:"syncMenu", icon:"https://user-images.githubusercontent.com/8125308/32844263-8ca0778e-c9e7-11e7-8d2d-d20182c08af1.png"
        }

        standardTile("forward", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Forward", action:"forward", icon:"https://user-images.githubusercontent.com/8125308/32845404-3a5ee11a-c9ea-11e7-8367-a8d0b5a67d8c.png"
        }

        standardTile("play", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Play", action:"play", icon:"https://user-images.githubusercontent.com/8125308/32845562-9fad0560-c9ea-11e7-9e6a-ec28239a87fb.png"
        }

        standardTile("rewind", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Rewind", action:"rewind", icon:"https://user-images.githubusercontent.com/8125308/32845459-5971e4a8-c9ea-11e7-8f05-24602acb2723.png"
        }

        standardTile("prev", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Prev", action:"prev", icon:"https://user-images.githubusercontent.com/8125308/32845696-060c9a00-c9eb-11e7-9ab8-56188bdb9b9f.png"
        } 

        standardTile("stop", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Stop", action:"stop", icon:"https://user-images.githubusercontent.com/8125308/32846108-f9d850c0-c9eb-11e7-8399-ae971296b3cb.png"
        }

        standardTile("next", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Next", action:"next", icon:"https://user-images.githubusercontent.com/8125308/32845664-eef4746e-c9ea-11e7-97db-675c0cbbe89c.png"
        }

        standardTile("rec", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Rec", action:"rec", icon:"https://user-images.githubusercontent.com/8125308/32846137-0f934794-c9ec-11e7-90f9-b36b47a8ee83.png"
        }

        standardTile("pause", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Pause", action:"pause", icon:"https://user-images.githubusercontent.com/8125308/32845505-751c75d8-c9ea-11e7-9091-4656ad486ff7.png"
        }

        standardTile("eject", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Eject", action:"eject", icon:""
        }

        standardTile("oneTouchView", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchView", action:"oneTouchView", icon:""
        } 

        standardTile("OoneTouchTimeRec", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchTimeRec", action:"oneTouchTimeRec", icon:""
        } 

        standardTile("oneTouchRec", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchRec", action:"oneTouchRec", icon:""
        } 

        standardTile("oneTouchStop", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchStop", action:"oneTouchStop", icon:""
        } 

        standardTile("DUX", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Discovery", action:"DUX", icon:""
        } 

        standardTile("footballMode", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Football Mode", action:"FootballMode", icon:""
        }

        controlTile("volume", "device.level", "slider", height: 1,width: 2) {
    	    state "level", action:"switch level.setLevel"
	    }

        main "switch"

        details(["switch", "TVMulti", "actionMenu", "cursorUp", "home", "pause", "play", "cursorLeft", "confirm", "cursorRight", "rewind", "forward", "prev", "next", "exit", "cursorDown", "cursorReturn", "picOff", "refresh"])
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

	def commandJson = "{\"id\":99,\"method\":\"getRemoteControllerInfo\",\"version\":\"1.0\",\"params\":[]}"
    sendJsonRpcCommand(commandJson)
    runEvery1Minute("pushUpdate")
    //subscribeAction("RenderingControl", "52323")
}

// parse events into attributes
def parse(description) {

    def msg = parseLanMessage(description)
    log.debug "parse Message.json '${msg.json}'"
    
    if (msg.json?.id == 99) {
        msg.json.result[1].each {
            log.debug "command -> ${it}"
        }
    }
    
    if (msg.json?.id == 2) {
        def tv = (msg.json.result[0]?.status == "active") ? "on" : "off"
        if (tv == "on") {
            def volumeJson = "{\"id\":3,\"method\":\"getVolumeInformation\",\"version\":\"1.0\",\"params\":[]}"
            sendJsonRpcCommand(volumeJson, "/sony/audio")
        }
        if (tv != state.tv) {
        	log.debug "parse change state: ${tv}"
        	state.tv = tv
        	sendEvent(name: "switch", value: tv)
        }
    }
    
    if (msg.json?.id == 3) {
        log.debug "parse Message.json for id = 3 '${msg.json}'"
    }
    
    if (msg.json?.id == 4) {
        log.debug "parse Message.json for id = 4 '${msg.json}'"
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

private pushUpdate() {

    def powerJson = "{\"id\":2,\"method\":\"getPowerStatus\",\"version\":\"1.0\",\"params\":[]}"
    sendJsonRpcCommand(powerJson)
    def volumeJson = "{\"id\":3,\"method\":\"getVolumeInformation\",\"version\":\"1.0\",\"params\":[]}"
    sendJsonRpcCommand(volumeJson, '/sony/audio')
    def inputJson = "{\"id\":4,\"method\":\"getPlayingContentInfo\",\"version\":\"1.0\",\"params\":[]}"
    sendJsonRpcCommand(inputJson, '/sony/avContent')
}

/*
private subscribeAction(path, port, callbackPath="") {

    log.debug "subscribeAction()"
    def address = getCallBackAddress()
    def ip = getDataValue("ip")
    
    def result = new physicalgraph.device.HubAction(
        method: "SUBSCRIBE",
        path: path,
        headers: [
            HOST: "http://${ip}:${port}/upnp/event/${path}",
            CALLBACK: "<http://${address}/notify${callbackPath}>",
            NT: "upnp:event",
            TIMEOUT: "Second-28800"
        ]
    )

    log.debug "SUBSCRIBE http://${ip}:${port}/upnp/event/${path}"
    return result
}
*/

private getCallBackAddress() {
    return device.hub.getDataValue("localIP") + ":" + device.hub.getDataValue("localSrvPortTCP")
}

// handle commands

private sendRemoteCommand(rawcmd){

	def sonycmd = new physicalgraph.device.HubSoapAction(
            path:    '/sony/IRCC',
            urn:     "urn:schemas-sony-com:service:IRCC:1",
            action:  "X_SendIRCC",
            body:    ["IRCCCode":rawcmd],
            headers: [Host:"${getDataValue("ip")}:${getDataValue("port")}", 'X-Auth-PSK':"${getDataValue("tvPSK")}"]
     )
     sendHubCommand(sonycmd)
}

private sendJsonRpcCommand(json, path='/sony/system') {

  def result = new physicalgraph.device.HubAction(
    method: 'POST',
    path: "${path}",
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

def setVolume(vol) {

	def json = "{\"method\":\"setAudioVolume\",\"version\":\"1.0\",\"params\":[{\"target\":\"speaker\", \"volume\":${vol}}],\"id\":4}"
    def result = sendJsonRpcCommand(json)
    sendEvent(name: "switch", value: "off")
    state.tv = "off"
}

def volumeUp() {

	log.debug "Executing 'volumeUp'"
    
	def rawcmd = "AAAAAQAAAAEAAAASAw=="
	sendRemoteCommand(rawcmd)
}

def volumeDown() {

	log.debug "Executing 'volumeDown'"

	def rawcmd = "AAAAAQAAAAEAAAATAw=="
	sendRemoteCommand(rawcmd)
}

def channelUp() {

	log.debug "Executing 'channelUp'"

	def rawcmd = "AAAAAQAAAAEAAAAQAw=="
	sendRemoteCommand(rawcmd)
}

def channelDown() {

	log.debug "Executing 'channelDown'"

	def rawcmd = "AAAAAQAAAAEAAAARAw=="
	sendRemoteCommand(rawcmd)
}

def cursorUp() {

	log.debug "Executing 'cursorUp'"

	def rawcmd = "AAAAAQAAAAEAAAB0Aw=="
	sendRemoteCommand(rawcmd)
}

def cursorDown() {

	log.debug "Executing 'cursorDown'"

	def rawcmd = "AAAAAQAAAAEAAAB1Aw=="
	sendRemoteCommand(rawcmd)
}

def cursorLeft() {

	log.debug "Executing 'cursorLeft'"

	def rawcmd = "AAAAAQAAAAEAAAA0Aw=="
	sendRemoteCommand(rawcmd)
}

def cursorRight() {

	log.debug "Executing 'cursorRight'"

	def rawcmd = "AAAAAQAAAAEAAAAzAw=="
	sendRemoteCommand(rawcmd)
}

def cursorReturn(){
	
    log.debug "Executing 'cursorReturn'"
    
	def rawcmd = "AAAAAgAAAJcAAAAjAw="
	sendRemoteCommand(rawcmd)
}

def cursorEnter(){
	
    log.debug "Executing 'cursorEnter'"
    
	def rawcmd = "AAAAAQAAAAEAAAALAw=="
    sendRemoteCommand(rawcmd)
}

def confirm() {

    log.debug "Executing 'confirm'"

    def rawcmd = "AAAAAQAAAAEAAABlAw=="
    sendRemoteCommand(rawcmd)
}

def actionMenu(){

    log.debug "Executing 'actionMenu'"

	def rawcmd = "AAAAAgAAAJcAAAA2Aw=="
    sendRemoteCommand(rawcmd)
}

def forward() {

    log.debug "Executing 'forward'"

	def rawcmd = "AAAAAgAAAJcAAAAcAw=="
    sendRemoteCommand(rawcmd)
}

def play(){

    log.debug "Executing 'play'"
    
	def rawcmd = "AAAAAgAAAJcAAAAaAw=="
    sendRemoteCommand(rawcmd)
}

def rewind(){

    log.debug "Executing 'rewind'"

	def rawcmd = "AAAAAgAAAJcAAAAbAw=="
    sendRemoteCommand(rawcmd)
}

def prev(){

    log.debug "Executing 'prev'"

	def rawcmd = "AAAAAgAAAJcAAAA8Aw=="
    sendRemoteCommand(rawcmd)
}

def stop(){

    log.debug "Executing 'stop'"

	def rawcmd = "AAAAAgAAAJcAAAAYAw=="
    sendRemoteCommand(rawcmd)
}

def next(){

    log.debug "Executing 'next'"

	def rawcmd = "AAAAAgAAAJcAAAA9Aw=="
    sendRemoteCommand(rawcmd)
}

def rec(){

    log.debug "Executing 'rec'"

	def rawcmd = "AAAAAgAAAJcAAAAgAw=="
    sendRemoteCommand(rawcmd)
}

def pause(){

    log.debug "Executing 'pause'"

	def rawcmd = "AAAAAgAAAJcAAAAZAw=="
    sendRemoteCommand(rawcmd)
}

def netflix() {

    log.debug "Executing 'netflix'"
    
	def rawcmd =  "AAAAAgAAABoAAAB8Aw=="
	sendRemoteCommand(rawcmd)
}

def sceneSelect() {

    log.debug "Executing 'sceneSelect'"
    
	state.remotecommand = "AAAAAgAAABoAAAB4Aw=="
	sendRemoteCommand(rawcmd)
}

def mode3D(){
	
    log.debug "Executing 'mode3D'"
    
	def rawcmd =  "AAAAAgAAAHcAAABNAw=="
	sendRemoteCommand(rawcmd)
}

def home() {

	log.debug "Executing 'home'"
    
    def rawcmd = "AAAAAQAAAAEAAABgAw=="
    sendRemoteCommand(rawcmd)
}

def exit() {

	log.debug "Executing 'exit'"
    
    def rawcmd = "AAAAAQAAAAEAAABjAw=="
    sendRemoteCommand(rawcmd)
}

def mute() {

	log.debug "Executing 'mute'"
    
    def rawcmd = "AAAAAQAAAAEAAAAUAw=="
    sendRemoteCommand(rawcmd)
}


def red(){

	log.debug "Executing 'red'"
    
	state.remotecommand = "AAAAAgAAAJcAAAAlAw=="
	sendRemoteCommand(rawcmd)
}

def green(){

	log.debug "Executing 'green'"
    
	def rawcmd = "AAAAAgAAAJcAAAAmAw=="
	sendRemoteCommand(rawcmd)
}

def yellow(){

	log.debug "Executing 'yellow'"
    
	def rawcmd = "AAAAAgAAAJcAAAAnAw=="
	sendRemoteCommand(rawcmd)
}

def blue(){

	log.debug "Executing 'blue'"
    
	def rawcmd = "AAAAAgAAAJcAAAAkAw=="
	sendRemoteCommand(rawcmd)
}

def num1(){

	log.debug "Executing 'num1'"
    
	def rawcmd = "AAAAAQAAAAEAAAAAAw=="
	sendRemoteCommand(rawcmd)
}

def num2(){

	log.debug "Executing 'num2'"
    
	def rawcmd = "AAAAAQAAAAEAAAABAw=="
	sendRemoteCommand(rawcmd)
}

def num3(){

	log.debug "Executing 'num3'"
    
	def rawcmd = "AAAAAQAAAAEAAAACAw=="
	sendRemoteCommand(rawcmd)
}

def num4(){

	log.debug "Executing 'num4'"
    
	def rawcmd = "AAAAAQAAAAEAAAADAw=="
	sendRemoteCommand(rawcmd)
}

def num5(){

	log.debug "Executing 'num5'"
    
	def rawcmd = "AAAAAQAAAAEAAAAEAw=="
	sendRemoteCommand(rawcmd)
}

def num6(){

	log.debug "Executing 'num6'"
    
	def rawcmd = "AAAAAQAAAAEAAAAFAw=="
	sendRemoteCommand(rawcmd)
}

def num7(){

	log.debug "Executing 'num7'"
    
	def rawcmd = "AAAAAQAAAAEAAAAGAw=="
	sendRemoteCommand(rawcmd)
}

def num8(){

	log.debug "Executing 'num8'"
    
	def rawcmd = "AAAAAQAAAAEAAAAHAw=="
	sendRemoteCommand(rawcmd)
}

def num9(){

	log.debug "Executing 'num9'"
    
	def rawcmd = "AAAAAQAAAAEAAAAIAw=="
	sendRemoteCommand(rawcmd)
}

def num0(){

	log.debug "Executing 'num0'"
    
	def rawcmd = "AAAAAQAAAAEAAAAJAw=="
	sendRemoteCommand(rawcmd)
}

def num11(){

	log.debug "Executing 'num11'"
    
	def rawcmd = "AAAAAQAAAAEAAAAKAw=="
	sendRemoteCommand(rawcmd)
}

def num12(){

	log.debug "Executing 'num12'"
    
	def rawcmd = "AAAAAQAAAAEAAAALAw=="
	sendRemoteCommand(rawcmd)
}

def picOff(){

	log.debug "Executing 'picOff'"
    
	def rawcmd = "AAAAAQAAAAEAAAA+Aw=="
    sendRemoteCommand(rawcmd)
}

def tvSource() {

	log.debug "Executing tvSource"
    
    def rawcmd = "AAAAAQAAAAEAAAAlAw=="
    sendRemoteCommand(rawcmd)
}


def hdmi1() {

	log.debug "Executing hdmi1"
    
    def rawcmd = "AAAAAgAAABoAAABaAw=="
    sendRemoteCommand(rawcmd)
}

def hdmi2() {

	log.debug "Executing hdmi2"
    
    def rawcmd = "AAAAAgAAABoAAABbAw=="
    sendRemoteCommand(rawcmd)
}

def hdmi3() {

	log.debug "Executing hdmi3"
    
    def rawcmd = "AAAAAgAAABoAAABcAw=="
    sendRemoteCommand(rawcmd)
}

def hdmi4() {

	log.debug "Executing hdmi4"
    
    def rawcmd = "AAAAAgAAABoAAABdAw=="
    sendRemoteCommand(rawcmd)
}

def WOLC() {

    log.debug "WOLC mac: ${device.deviceNetworkId}"
    
	def result = new physicalgraph.device.HubAction (
        "wake on lan ${device.deviceNetworkId}",
   		physicalgraph.device.Protocol.LAN,
   		null,
    	[secureCode: "111122223333"]
	)
	return result
}