package com.developer.timurnav.fungames.domain

data class TileTo(
        val id: Int,
        private var value: Int
) {

    private var removedTileId: Int? = null

    fun upgrade(removedTileId: Int): TileTo {
        value = value shl 1
        this.removedTileId = removedTileId
        return this
    }

    fun ifUpgraded(remove: (Int) -> Unit) {
        removedTileId?.let {
            remove(it)
            removedTileId = null
        }
    }

    fun value() = value
    override fun toString(): String {
        return "TileTo(id=$id, value=$value, removedTileId=$removedTileId)"
    }


}
