<div id="import-tel-transfer" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">导入电汇收款单</h4>
    </div>
    <div class="modal-body">
        <div class="control-group span6">
            <label class="control-label">收款银行账户</label>

            <div class="controls">
                <select id="tt-import-bankId" name="bankId" class="form_value">
                    <option value="0">请选择收款银行账户</option>
                </select>
            </div>
        </div>

        <label class="import-label">导入文件：</label>
        <div class="uploader">
            <input id="import-file" class="import-file" style="display: none" name="telTransferFile" type="file" size="31">

            <div class="input-append">
                <input type="text" name="upload-file" data-bind="value: filename" placeholder="这里是文件名">
                <button id="chooseFile" class="btn btn-default btn-fs-default btn-fs-sm" type="button">选择文件</button>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-default btn-fs-default btn-fs-xs" data-dismiss="modal" aria-hidden="true">取消</button>
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="confirm-import">确定</button>
    </div>
</div>
<div id="tel-transfer-result" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-labelledby="tel-transfer-result"
     aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="tel-transfer-result">导入电汇收款单结果</h4>
    </div>
    <div class="modal-body">
        <div class="success-result"></div>
        <div class="failed-result"></div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="confirm-result" data-dismiss="modal"
                aria-hidden="true">确定
        </button>
    </div>
</div>

<div class="panel">
    <div class="header">
        <h4>收款单查询</h4>
    </div>
    <div class="body">
        <div class="form-horizontal" id="ro-search-form">
            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">产品线<span class="required">*</span></label>
                    <div class="controls">
                        <select id="q-businessType" name="businessType" class="form_value" validate="ne[0]"
                                error_msg="ne[0]:请选择产品线">
                            <option value="0">请选择产品线</option>
                        </select>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">客户ID</label>
                    <div class="controls">
                        <input type="text" id="q-customerId" name="customerId" class="form_value"  validate="n">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">业务类型</label>
                    <div class="controls">
                        <select id="q-receiveType" name="receiveType" class="form_value">
                            <option value="0">请选择业务类型</option>
                        </select>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">收款方式</label>
                    <div class="controls">
                        <select id="q-payChannel" name="payChannel" class="form_value">
                            <option value="0">请选择收款方式</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">银行到账日期</label>

                    <div class="controls">
                        <select id="q-select-br-time" class="select-small">
                            <option value="99">自定义</option>
                            <option value="0">截至今天</option>
                            <option value="1">今天</option>
                            <option value="2">昨天</option>
                            <option value="3">前天</option>
                        </select>

                        <div class="input-append">
                            <input type="text" id="q-br-begin-time" datatype="date" name="bankReceiveTimeBegin"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                        至
                        <div class="input-append">
                            <input type="text" id="q-br-end-time" validate="ge[q-br-begin-time]" datatype="date"
                                   name="bankReceiveTimeEnd" error_msg="ge[q-br-begin-time]:起始日期大于结束日期"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">系统收款日期</label>

                    <div class="controls">
                        <select id="q-select-receive-time" class="select-small">
                            <option value="99">自定义</option>
                            <option value="0">截至今天</option>
                            <option value="1">今天</option>
                            <option value="2">昨天</option>
                            <option value="3">前天</option>
                        </select>

                        <div class="input-append">
                            <input type="text" id="q-receive-begin-time" datatype="date" name="receiveTimeBegin"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                        至
                        <div class="input-append">
                            <input type="text" id="q-receive-end-time" validate="ge[q-receive-begin-time]" datatype="date"
                                   name="receiveTimeEnd" error_msg="ge[q-receive-begin-time]:起始日期大于结束日期"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                    </div>
                </div>


            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">确认收款情况</label>
                    <div class="controls">
                        <select id="q-status" name="status" class="form_value">
                            <option value="0">请选择收款情况</option>
                        </select>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">收款银行账户</label>
                    <div class="controls">
                        <select id="q-bankId" name="bankId" class="form_value">
                            <option value="0">请选择收款银行账户</option>
                        </select>
                    </div>
                </div>
            </div>


            <div class="row-fluid">
                <div class="control-group span6">
                    <label class="control-label"></label>

                    <div class="controls">
                        <a class="btn btn-primary btn-fs-normal btn-fs-xs" href="#"
                           id="search">
                            <span class="glyphicon glyphicon-search"></span>查询
                        </a>

                        <a id="btn-add-br" style="width: 100px" href="#add-ro-dialog" role="button" class="btn btn-primary btn-fs-normal btn-fs-xs ajaxdisabledbutton" data-toggle="modal">
                            <span class="glyphicon glyphicon-pencil"></span>手工录入</a>

                        <a id="btn-import-tel-transfer" href="#import-tel-transfer" role="button" class="btn btn-primary btn-fs-normal btn-fs-xs ajaxdisabledbutton" data-toggle="modal">
                            <span class="glyphicon glyphicon-file"></span>导入电汇收款单</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>