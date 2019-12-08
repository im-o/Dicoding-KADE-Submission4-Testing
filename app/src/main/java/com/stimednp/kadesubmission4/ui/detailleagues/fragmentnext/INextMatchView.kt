package com.stimednp.kadesubmission4.ui.detailleagues.fragmentnext

import com.stimednp.kadesubmission4.base.IView
import com.stimednp.kadesubmission4.model.ResponseEvents
import com.stimednp.kadesubmission4.presenter.detailleagues.fragmentnext.INextRepositoryCallback

/**
 * Created by rivaldy on 12/8/2019.
 */

interface INextMatchView: INextRepositoryCallback<ResponseEvents>, IView {
    fun showMsgSucces(text: String)
    fun showMsgFail(text: String)
    fun showTextEmpty(text: String)
    fun hideTextEmpty()
}