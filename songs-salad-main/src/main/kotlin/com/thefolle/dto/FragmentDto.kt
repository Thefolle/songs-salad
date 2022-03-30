package com.thefolle.dto

class FragmentDto (
    var id: Long?,
    // the structural position of the fragment, not about the execution order
    var position: Long,
    var text: String,
    var isChorus: Boolean
)