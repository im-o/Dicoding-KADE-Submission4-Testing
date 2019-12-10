package com.stimednp.kadesubmission4.ui.detailleagues.fragementlast

import com.stimednp.kadesubmission4.base.IView
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentlast.ILastRepositoryCallback

/**
 * Created by rivaldy on 12/8/2019.
 */

interface ILastMatchView : ILastRepositoryCallback<ResponseEvents>, IView {
    fun showMsgSucces(text: String)
    fun showMsgFail(text: String)
    fun showTextEmpty(text: String)
    fun hideTextEmpty()
}