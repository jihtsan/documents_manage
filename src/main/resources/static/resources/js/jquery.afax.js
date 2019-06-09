/**
 * Created by C0dEr on 2017/7/6.
 */
;(function ($) {


    var customAjax = {
        init: function (initData) {
            var filter = function (data) {
                if (data.statusCode != undefined && data.statusCode == 1) {
                    console.log(data.message)
                    layer.open({
                        title: '提示',
                        content: data.message,
                        resize: false,
                        time: 5000
                    });
                    return false;
                }
                return true;
            }

            if (initData.success != undefined && typeof initData.success == 'function') {
                var orgFun = {}
                orgFun["success"] = initData["success"]
                initData.success = function (data) {
                    if (!filter(data)) {
                        return;
                    }
                    orgFun.success(data)
                }
            }

            $.ajax(initData)
        }
    }

    $.extend(
        {
            afax: function (aj) {
                customAjax.init(aj)
            }
        }
    )

})
(jQuery)
