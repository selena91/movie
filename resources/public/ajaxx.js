$('div#submituser').click(function(){
            var data = $(".btn-select").text();
            $.ajax({
                url: 'ajaxcall',
                type: 'POST',
                data: {iduser: data},
                beforeSend: function(){
                   $('div#loaderdiv').show();
                },
                success: function(result){
                $('div#loaderdiv').hide();
                $('#div1').html(result);
                }});
    });
