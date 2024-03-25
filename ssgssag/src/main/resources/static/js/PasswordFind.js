$('#password-reset-btn').on('click', function () {
    console.log("호출");
    let id = $('#input-id').val();

    console.log("1" + id)

    $.ajax({
        url: '/find-email',
        type: 'POST',
        // contentType: 'application/x-www-form-urlencoded',
        data: { id: id },
        success: function (response) {
            console.log("sucess");
        },
        error: function (xhr, status, error) {
            console.error('에러 발생:', error);
        }
    });
});