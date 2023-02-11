package com.weiran.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class GoogleDocsApplication

fun main(args: Array<String>) {
    runApplication<GoogleDocsApplication>(*args)
}
