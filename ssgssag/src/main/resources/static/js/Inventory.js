$(document).ready(function () {
    console.log("실행");
    getWarehouseAndZone();
    getCategoryHierarchy();
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
            console.log('서버 응답:');
            console.log("여기가 진짜야!!!!!!!" + JSON.stringify(data))
            // 서버 응답에 따른 처리
        },
        error: function(xhr, status, error) {
            console.error('에러 발생:', error);
            // 에러 처리
        }
    });
});


