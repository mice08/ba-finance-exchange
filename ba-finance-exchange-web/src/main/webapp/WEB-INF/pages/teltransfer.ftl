<div id="import-tel-transfer" class="modal hide fade" style="z-index:50;left:auto;width:80%;margin-left: auto;margin-right: auto;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">导入电汇收款单</h4>
    </div>
    <div class="modal-body">
        <table>
            <tbody>
                <tr>
                    <td>
                        <label class="control-label">收款银行账户</label>
                        <div class="">
                            <select id="tt-import-bankId" name="bankId" class="form_value">
                                <option value="0">请选择收款银行账户</option>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="import-label">导入文件：</label>
                        <div class="uploader">
                            <input id="import-file" class="import-file" style="display: none" name="telTransferFile"
                                   type="file"
                                   size="31">

                            <div class="input-append">
                                <input type="text" name="upload-file" data-bind="value: filename" placeholder="这里是文件名">
                                <button id="choose-file" class="btn btn-default btn-fs-default btn-fs-sm" type="button">
                                    选择文件
                                </button>
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <button class="btn btn-default btn-fs-default btn-fs-xs" data-dismiss="modal" aria-hidden="true">取消
        </button>
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="confirm-import">确定</button>
    </div>
</div>


<div id="tel-transfer-result" class="modal hide fade"
     style="z-index:50;left:auto;width:80%;margin-left: auto;margin-right: auto;" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">导入电汇收款单结果</h4>
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