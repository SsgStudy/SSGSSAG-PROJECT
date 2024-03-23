$(document).ready(function () {
    console.log("실행");
    $(".alert").hide();
    getWarehouseAndZone();
    getCategoryHierarchy();

    // 수량 조절
    $('#out-bound').change(function () {
        $('.inventory-checkbox').change(handleCheckboxChange);
    });

// 체크박스 조절
    $('.inventory-checkbox').change(function() {
        $('.inventory-checkbox').not(this).prop('checked', false);
    });
});

// 재고 이력 모달창
function handleInventoryDetailLinkClick(key) {
    console.log("handleInventoryDetailLinkClick 호출");

    // AJAX 요청
    $.ajax({
        url: '/inventory/list/detail/' + key,
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            console.log(data)

            // 기존의 행들을 모두 제거
            $('#inventory-history-modal-body').empty();

            // 새로운 행을 생성하여 tbody에 추가
            let formattedDate = formatDate(data.dtInventoryChangeDate);

            let changeType;
            switch (data.vinventoryChanegeType) {
                case 'CHANGE_CNT_INBOUND' :
                    changeType = '입고 수량 조절';
                    break;
                case 'CHANGE_CNT_OUTBOUND' :
                    changeType = '출고 수량 조절';
                    break;
                case 'MOVE' :
                    changeType = '창고 이동';
                    break;
            }

            let row = `<tr role="row" class="odd">
                    <td>${data.pkInventorySeq}</td>
                    <td>${data.ninventoryShippingCnt}</td>
                    <td>${formattedDate}</td>
                    <td>${changeType}</td>
                    <td>${data.vzoneCd}</td>
                    <td>${data.vwarehouseCd}</td>
                    <td>${data.vzoneCd2}</td>
                    <td>${data.vwarehouseCd2}</td>
                </tr>`;

            $('#inventory-history-modal-body').append(row);
        },

        error: function (xhr, status, error) {
            // 데이터를 받아오지 못했을 때 실행되는 함수
            console.error('Error fetching data:', error);

            $('#inventory-history-modal-body').empty();
        }
    });
}

// 카테고리 입력폼
$('#select-main-category').change(function () {
    let $subCategory = $('#select-sub-category');
    $subCategory.empty();
    $subCategory.append(
        `<option value="중분류">중분류</option>`
    );

    $('#select-detail-category').empty();
    $('#select-detail-category').append(`
       <option value="소분류">소분류</option>`
    );

    if ($('#select-main-category').val() === "대분류")
        return;

    let mainCategory = $('#select-main-category').val();
    renderSubCategory(mainCategory);
});

$('#select-sub-category').change(function () {
    let $detailCategory = $('#select-detail-category');
    $detailCategory.empty();
    $detailCategory.append(
        `<option value="소분류">소분류</option>`
    );
    let mainCategory = $('#select-main-category').val();
    let subCategory = $('#select-sub-category').val();

    if (subCategory === "중분류")
        return
    renderDetailCategory(mainCategory, subCategory);
});


var categoryMap = new Map();

// 카테고리 대분류
function getCategoryHierarchy() {
    $.ajax({
        url: '/inventory/category',
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            data.forEach(function (item) {
                let mainCategory = item.vmainCategoryNm;
                let subCategory = item.vsubCategoryNm;
                let detailCategory = item.vdetailCategoryNm;

                // 대분류 추가
                if (!categoryMap.has(mainCategory)) {
                    categoryMap.set(mainCategory, new Map());
                }

                // 중분류 추가
                if (!categoryMap.get(mainCategory).has(subCategory)) {
                    categoryMap.get(mainCategory).set(subCategory, []);
                }

                // 소분류 추가
                categoryMap.get(mainCategory).get(subCategory).push(detailCategory);

            });

            renderMainCategory();

        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}


function renderMainCategory() {
    console.log("render 호출")

    let keys = Array.from(categoryMap.keys());

    keys.forEach(function (category) {
        $('#select-main-category').append(
            `<option value="${category}">${category}</option>`
        );
    });
}

function renderSubCategory(mainCategory) {
    console.log("render 호출")

    let mainCategoryMap = categoryMap.get(mainCategory);
    let keys = Array.from(mainCategoryMap.keys());

    keys.forEach(function (category) {
        $('#select-sub-category').append(
            `<option value="${category}">${category}</option>`
        );
    });
}

function renderDetailCategory(mainCategory, subCategory) {
    console.log("render 호출")

    let subCategoryMap = categoryMap.get(mainCategory).get(subCategory);

    console.log(subCategoryMap);
    let keys = Array.from(subCategoryMap);

    keys.forEach(function (category) {
        $('#select-detail-category').append(
            `<option value="${category}">${category}</option>`
        );
    });
}


// 창고 입력폼
$('#select-warehouse').change(function () {
    let $zone = $('#select-zone');
    $zone.empty();
    $zone.append(
        `<option value="구역">구역</option>`
    );
    let warehouse = $('#select-warehouse').val();
    if (warehouse === "창고")
        return
    renderWarehouseZone(warehouse);
});


// Warehouse 이름을 key로, Zone 이름을 value로 하는 Map 객체
let warehouseMap = new Map();

function getWarehouseAndZone() {
    $.ajax({
        url: '/inventory/warehouse',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let warehouseZones = {};

            $.each(data, function (idx, ware) {
                let warehouseName = ware.vwarehouseNm;
                let zoneName = ware.vzoneNm;

                if (!warehouseMap.has(warehouseName)) {
                    warehouseMap.set(warehouseName, []);
                }
                warehouseMap.get(warehouseName).push(zoneName);

                if (!warehouseZones[warehouseName]) {
                    warehouseZones[warehouseName] = [];
                }
                warehouseZones[warehouseName].push(zoneName);
                console.log("before");

            });

            renderWarehouseNm();
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}


function renderWarehouseNm() {
    let keys = Array.from(warehouseMap.keys());

    keys.forEach(function (warehouse) {
        $('#select-warehouse').append(
            `<option value="${warehouse}">${warehouse}</option>`
        );
    });
}

// 구역 코드 렌더링
function renderWarehouseZone(warehouseNm) {
    let zoneList = warehouseMap.get(warehouseNm);

    zoneList.forEach(function (zone) {
        $('#select-zone').append(
            `<option value="${zone}">${zone}</option>`
        );
    });

}



// 재고 목록 checkbox 선택

let selectedNumber
$('.inventory-checkbox').on('change', function() {
    if ($(this).prop('checked')) {
        selectedNumber = $(this).data('inventory-id');
        console.log(selectedNumber);
    }
});


// input box 수량 조
let selectCnt;

function handleCheckboxChange() {
    let selectedRow = $(this).closest('tr');
    selectCnt = selectedRow.find('.inventory-quantity').text();
}

function handleOutputQuantityChange() {
    let currentValue = parseInt($('#adjustment-cnt').val()); // 현재 입력된 값
    let minValue = 1; // 최소 값
    let maxValue = selectCnt; // 최대 값

    // 입력된 값이 범위를 벗어나는 경우
    if (currentValue < minValue || currentValue > maxValue) {
        // 안내 메시지 표시
        $('#warningMessage').text('조건에 맞는 수량을 입력해주세요').show();
    } else {
        // 범위 내에 있는 경우 안내 메시지 숨기기
        $('#warningMessage').hide();
    }
}

$('#adjustment-cnt').on('input', function () {
    handleOutputQuantityChange();
});




$('#submitButton').on('click', function() {
    let quantity = parseFloat($('#adjustment-cnt').val());
    let type = $('#in-bound').prop('checked') ? 'CHANGE_CNT_INBOUND' : 'CHANGE_CNT_OUTBOUND';

    let data = {
        pkInventorySeq: selectedNumber,
        vInventoryChangeType: type,
        nInventoryShippingCnt: quantity
    };

    console.log(data)

    // JSON 데이터를 POST로 서버에 보냄
    $.ajax({
        url: '/inventory/adjustment/update',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            console.log('서버 응답:'+ JSON.stringify(data));

            // 성공 시 초록 alert창 띄우기
            showSuccessAlert();
            window.scrollTo(0, 0);
            // reloadInventoryTable();
            reloadPageAfterDelay(1000);
            // 페이지 리로딩
        },
        error: function(xhr, status, error) {
            console.error('에러 발생:', error);

            // 실패 시 에러 alert창 띄우기
            showDangerAlert();
            window.scrollTo(0, 0);
            // reloadInventoryTable();
            reloadPageAfterDelay(1000);
            // 페이지 리로딩
            // location.reload();
        }
    });
});


// alert
function showSuccessAlert() {
    $("#successAlert").fadeIn(); // 알림 보이기
    setTimeout(function() {
        $("#successAlert").fadeOut(); // 몇 초 후에 다시 숨기기
    }, 3000); // 3초 후에 숨김
}
function showDangerAlert() {
    $("#dangerAlert").fadeIn(); // 알림 보이기
    setTimeout(function() {
        $("#dangerAlert").fadeOut(); // 몇 초 후에 다시 숨기기
    }, 3000); // 3초 후에 숨김
}

function reloadPageAfterDelay(delay) {
    setTimeout(function() {
        location.reload(); // 페이지 리로드
    }, delay); // 지연 시간 (밀리초)
}

$("#reloadPageBtn").on("click", function() {
    location.reload(); // 페이지 다시 로드
});
