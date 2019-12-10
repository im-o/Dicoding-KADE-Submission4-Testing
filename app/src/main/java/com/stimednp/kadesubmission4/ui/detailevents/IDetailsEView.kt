package com.stimednp.kadesubmission4.ui.detailevents

import com.stimednp.kadesubmission4.base.IView
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.presenter.detailematch.IDetailsERepositoryCallback

/**
 * Created by rivaldy on 12/9/2019.
 */

interface IDetailsEView : IDetailsERepositoryCallback<ResponseEvents>, IView {
    fun showMsgSucces(text: String)
    fun showMsgFail(text: String)
}