$(document).ready(function () {
    $('#btnSubmit').click(function (event) {
        event.preventDefault();
        
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);
        data.append('customField', '추가필드');
        $('#btnSubmit').prop('disabled', true);

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/sample/upload/multi/model',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                $('#result').text(data);
                console.log(data);
                console.log('SUCCESS : ', data);
                $('#btnSubmit').prop('disabled', false);
            },
            error: function (e) {
                $('#result').text(e.responseText);
                console.log('ERROR : ', e);
                $('#btnSubmit').prop('disabled', false);
            }
        });
        
    });
});