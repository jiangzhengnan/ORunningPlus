package com.oplayer.orunningplus.event

import java.text.FieldPosition


class MessageEvent {

    private var message: Any? = null
    private var messageType: Any? = null
    private var position: Any? = null

    constructor( messageType: Any,message: Any) {
        this.messageType = messageType
        this.message = message
    }
    constructor( messageType: Any) {
        this.messageType = messageType
    }

    //适配列表情况下 无法识别swit ch
    constructor( messageType: Any,message: Any,position: Int) {
        this.messageType = messageType
        this.message = message
        this.position = position
    }



    fun getMessage(): Any? {
        return message
    }

    fun setMessage(message: Any) {
        this.message = message
    }

    fun getMessageType(): Any? {
        return messageType
    }

    fun setMessageType(messageType: Any?) {
        this.messageType = messageType
    }

    override fun toString(): String {
        return "MessageEvent( messageType=$messageType  ,\n message=   $message,)"
    }


}