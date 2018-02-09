$(function(){

    var exposeChart = echarts.init(document.getElementById("expose_chart"));
    var clickChart  = echarts.init(document.getElementById("click_chart"));
    var ctrChart  = echarts.init(document.getElementById("ctr_chart"));

    var indexes = [/*'null',*/'icf','brand_hot','hot','cb','t3c','rl','tb','als'];

    function getIndex(name){
        for(var j=0; j< indexes.length;j++){
            if(indexes[j] === name) return j;
        }
        return -1;
    }

    function clone(obj){
        var buf;
        if(obj instanceof Array){
            buf = [];
            var i = obj.length;
            while(i--){
                buf[i] = clone(obj[i]);
            }
            return buf;
        }
        else if(obj instanceof Object){
            buf = {};
            for(var k in obj){
                buf[k] = clone(obj[k]);
            }
            return buf;
        }
        else{
            return obj;
        }
    }

    var option = {
        title: {
            text: '图表'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:[/*'null',*/'icf','brand_hot','hot','cb','t3c','rl','tb','als']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [
            // {
            //     name:'null',
            //     type:'line',
            //     stack: 'null',
            //     data:[]
            // },
            {
                name:'icf',
                type:'line',
                stack: 'icf',
                smooth:true,
                data:[]
            }
            //data:['null','icf','brand_hot','hot','cb','t3c','rl','tb','als']
            ,{
                name:'brand_hot',
                type:'line',
                stack: 'brand_hot',
                smooth:true,
                data:[]
            },{
                name:'hot',
                type:'line',
                stack: 'hot',
                smooth:true,
                data:[]
            },{
                name:'cb',
                type:'line',
                stack: 'cb',
                smooth:true,
                data:[]
            },{
                name:'t3c',
                type:'line',
                stack: 't3c',
                smooth:true,
                data:[]
            },{
                name:'rl',
                type:'line',
                stack: 'rl',
                smooth:true,
                data:[]
            },{
                name:'tb',
                type:'line',
                stack: 'tb',
                smooth:true,
                data:[]
            },{
                name:'als',
                type:'line',
                stack: 'als',
                smooth:true,
                data:[]
            }
        ]
    };

    var req = function(){
        $.post("/metrics/ctr",{},function(result1){
            var _option1 = clone(option);
            _option1.yAxis.axisLabel = {
                formatter: '{value}%'
            };
            //_option1.tooltip.formatter = 'CTR点击率 : <br/>{a} : {c}%';
            var _buffer1 = [];
            for(var i=0; i<result1.length; i++){
                if(_buffer1[result1[i].time] === undefined){
                    _buffer1[result1[i].time] = [];
                }

                _buffer1[result1[i].time][result1[i].name]=result1[i].count;
            }
            console.log(_buffer1);
            for(var j in _buffer1){
                _option1.xAxis.data.push(j);

                for(var k in indexes){
                    if(_buffer1[j][indexes[k]]===undefined){
                        _option1.series[k].data.push(0);
                    }else{
                        _option1.series[k].data.push(_buffer1[j][indexes[k]]);
                    }
                }
            }

            if (_option1 && typeof _option1 === "object") {
                // myChart.setOption(option, true);
                ctrChart.setOption(_option1,true);
            }
        });

        $.post("/metrics/count",{event:'expose'},function(result1){
            var _option1 = clone(option);
            var _buffer1 = [];
            for(var i=0; i<result1.length; i++){
                if(_buffer1[result1[i].time] === undefined){
                    _buffer1[result1[i].time] = [];
                }

                _buffer1[result1[i].time][result1[i].name]=result1[i].count;
            }
            console.log(_buffer1);
            for(var j in _buffer1){
                _option1.xAxis.data.push(j);

                for(var k in indexes){
                    if(_buffer1[j][indexes[k]]===undefined){
                        _option1.series[k].data.push(0);
                    }else{
                        _option1.series[k].data.push(_buffer1[j][indexes[k]]);
                    }
                }
            }

            if (_option1 && typeof _option1 === "object") {
                // myChart.setOption(option, true);
                exposeChart.setOption(_option1,true);
            }
        });

        $.post("/metrics/count",{event:'click'},function(result2){
            var _option2 = clone(option);
            var _buffer2 = [];
            for(var i=0; i<result2.length; i++){
                if(_buffer2[result2[i].time] === undefined){
                    _buffer2[result2[i].time] = [];
                }

                _buffer2[result2[i].time][result2[i].name]=result2[i].count;
            }
            console.log(_buffer2);
            for(var j in _buffer2){
                _option2.xAxis.data.push(j);

                for(var k in indexes){
                    if(_buffer2[j][indexes[k]]===undefined){
                        _option2.series[k].data.push(0);
                    }else{
                        _option2.series[k].data.push(_buffer2[j][indexes[k]]);
                    }
                }
            }

            console.log(_option2);
            if (_option2 && typeof _option2 === "object") {
                // myChart.setOption(option, true);
                clickChart.setOption(_option2,true);
            }
        });
    };
    $('#queryBtn').attr('disabled',"true");
    $('#stopBtn').removeAttr("disabled");
    starter=setInterval(req, 4000);
    //开启定时器
    $('#queryBtn').click(function(){
        req();

        $('#queryBtn').attr('disabled',"true");
        $('#stopBtn').removeAttr("disabled");
        starter=setInterval(req, 4000);
    });

    //清除定时器
    $('#stopBtn').click(function(){
        $('#queryBtn').removeAttr("disabled");
        $('#stopBtn').attr('disabled',"true");
        clearInterval(starter);
    });
});