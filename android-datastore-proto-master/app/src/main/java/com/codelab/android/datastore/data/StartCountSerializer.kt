package com.codelab.android.datastore.data

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.codelab.android.datastore.StartCount
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object StartCountSerializer : Serializer<StartCount> {
    override fun readFrom(input: InputStream): StartCount {
        try {
            return StartCount.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: StartCount, output: OutputStream) = t.writeTo(output)
}