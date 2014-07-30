<div class="panel">
    <div class="header">
        <h4>收款通知查询</h4>
    </div>
    <div class="body">
        <div class="form-horizontal" id="rn-search-form">
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
                    <label class="control-label">客户名</label>
                    <div class="controls">
                        <input type="text" id="q-customerName" name="customerName" class="form_value">
                        <input type="hidden" id="q-customerId" name="customerId" class="form_value"  validate="n">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">支付方式</label>
                    <div class="controls">
                        <select id="q-payChannel" name="payChannel" class="form_value">
                            <option value="0">全部</option>
                        </select>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">金额</label>
                    <div class="controls">
                        <input type="text" id="q-receiveAmount" name="receiveAmount" class="form_value">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">收款公司</label>
                    <div class="controls">
                        <select id="q-bankId" name="bankId" class="form_value">
                            <option value="0">全部</option>
                        </select>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">状态</label>
                    <div class="controls">
                        <select id="q-status" name="status" class="form_value">
                            <option value="0">全部</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">打款日期</label>

                    <div class="controls">
                        <select id="q-select-br-time" class="select-small">
                            <option value="99">自定义</option>
                            <option value="0">截至今天</option>
                            <option value="1">今天</option>
                            <option value="2">昨天</option>
                            <option value="3">前天</option>
                        </select>

                        <div class="input-append">
                            <input type="text" id="q-br-begin-time" datatype="date" name="receiveTimeBegin"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                        至
                        <div class="input-append">
                            <input type="text" id="q-br-end-time" validate="ge[q-br-begin-time]" datatype="date"
                                   name="receiveTimeEnd" error_msg="ge[q-br-begin-time]:起始日期大于结束日期"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>