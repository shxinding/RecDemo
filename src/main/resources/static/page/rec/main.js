$.ajaxSetup({
    async : false
});
$(function(){
    $('#queryBtn').click(function(){
        $.post("/page/rec",{memberId:$('#memberId').val()},function(result){
            html = "";
            for(var i=0; i<result.length; i++){
                html += "<tr>";
                $.post("http://item.51tiangou.com/front/listing/itemDetailInfo",{id:result[i]},function (r) {
                    console.log(r);
                    html += ""

                    html += "<td>"+result[i]+"</td>";
                    html += "<td>"+r.data.itemImageList[0].url+"</td>";
                    html += "<td>"+r.data.productInfo.productName +"</td>";

                });
                html += "</tr>";
            }

            $('#data').empty();
            $('#data').html(html);
        });
    });
});