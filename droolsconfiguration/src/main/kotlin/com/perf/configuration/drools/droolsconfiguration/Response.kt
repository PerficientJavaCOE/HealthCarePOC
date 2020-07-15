package com.perf.configuration.drools.droolsconfiguration

import java.io.File

data class Response (var code: Int = 0){
    var message : String = ""
    var f: File = File("f:\\program")
    var files = f.list()
}