package com.hr.models

import java.io.Serializable

data class Movie(val name: Name) : Serializable
inline class Name(val value: String)