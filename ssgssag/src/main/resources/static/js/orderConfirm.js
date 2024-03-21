$(document).ready(function() {
    getNowDate();

});

// 기타
function convertDateFormat(dateStr) {
    var parts = dateStr.split('/');
    return parts[2] + '-' + parts[0] + '-' + parts[1];
}

function getNowDate() {
    let now = new Date(); // 현재 날짜 및 시간
    let startDate = formatDate(new Date(now.getFullYear(), now.getMonth(), 1));
    let endDate = formatDate(new Date(now.getFullYear(), now.getMonth() + 1, 0));

    $('#order-period').val(startDate + ' - ' + endDate);
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [month, day, year].join('/');
}