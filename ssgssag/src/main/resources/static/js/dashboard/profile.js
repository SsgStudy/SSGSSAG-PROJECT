$(document).ready(function () {
    console.log('실행?');
    $.ajax({
        url: '/member/profile-img',
        method: 'GET',
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data) {
            if (data) {
                let urlCreator = window.URL || window.webkitURL;
                let imageUrl = urlCreator.createObjectURL(data);
                $('.user-profile-img').attr('src', imageUrl);
            } else {
                $('.user-profile-img').attr('src', '/images/profile/default-img.png');
            }
        },
        error: function(xhr, status, error) {
            console.log("응답 실패 ", error);
            $('.user-profile-img').attr('src', '/images/profile/default-img.png');
        }
    });
});
