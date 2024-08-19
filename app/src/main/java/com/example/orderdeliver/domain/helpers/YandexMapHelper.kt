package com.example.orderdeliver.domain.helpers

import com.example.orderdeliver.presentation.delivery.ManualChooseViewModel
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.runtime.Error

object YandexMapHelper {

    // TODO (Use typealias)

    fun convertAddressToPoint(searchManager: SearchManager, address: String, blockSuccess: (Point?) -> Unit, blockError: ((String) -> Unit)? = null) {
        searchManager.submit(
            address,
            Geometry.fromBoundingBox(ManualChooseViewModel.BOUNDING_BOX),
            SearchOptions(),
            object : Session.SearchListener {
                override fun onSearchResponse(response: Response) {
                    val obj = response.collection.children[0].obj
                    if (obj == null){
                        if (blockError == null) return
                        blockError("Objects aren't found, object == null")
                    }else {
                        blockSuccess(obj.geometry[0].point)
                    }

                }

                override fun onSearchError(error: Error) {
                    if (blockError == null) return
                    blockError("Search isn't success, error valid: ${error.isValid}")
                }

            })
    }

}