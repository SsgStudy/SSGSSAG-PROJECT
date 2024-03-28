$(document).ready(function () {
    $.ajax({
        url: '/member/profile-img',
        method: 'GET',
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data) {
            console.log(data);
            if (data.size > 0) {
                let urlCreator = window.URL || window.webkitURL;
                let imageUrl = urlCreator.createObjectURL(data);
                $('.user-profile-img').attr('src', imageUrl);
            }
        },
        error: function(xhr, status, error) {
            console.log("응답 실패 ", error);
            $('.user-profile-img').attr('src', '/images/profile/default-img.png');
        }
    });
});