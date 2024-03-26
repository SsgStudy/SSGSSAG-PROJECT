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
    $('.inventory-checkbox').change(function () {
        $('.inventory-checkbox').not(this).prop('checked', false);
    });
});

// 재고 이력 모달창
function handleInventoryDetailLinkClick(key) {
    console.log("handleInventoryDetailLinkClick 호출");

    $('#inventory-seq').text(key);

    // AJAX 요청
    $.ajax({
        url: '/inventory/list/detail/' + key,
        type: 'GET',
        dataType: 'json',
        success: function (data) {

            // console.log(data)

            // 기존의 행들을 모두 제거
            $('#inventory-history-modal-body').empty();

            let cnt = 1;
            data.forEach(function (item) {
                let formattedDate = formatDate(item.dtInventoryChangeDate); // 날짜 처리
                let shippingCnt = item.ninventoryShippingCnt; // 수량 처리

                let changeType;
                switch (item.vinventoryChanegeType) {
                    case 'CHANGE_CNT_INBOUND' :
                        changeType = '입고 수량 조절';
                        break;
                    case 'CHANGE_CNT_OUTBOUND' :
                        changeType = '출고 수량 조절';
                        break;
                    case 'MOVE' :
                        changeType = '창고 이동';
                        shippingCnt = '';
                        break;
                }

                let row = `<tr role="row" class="odd">
            <td>${cnt}</td>
<!--            <td>${item.pkInventorySeq}</td>-->
            <td>${shippingCnt}</td>
            <td>${formattedDate}</td>
            <td>${changeType}</td>
            <td>${item.vzoneCd}</td>
            <td>${item.vwarehouseCd}</td>
            <td>${item.vzoneCd2}</td>
            <td>${item.vwarehouseCd2}</td>
        </tr>`;

                $('#inventory-history-modal-body').append(row);

                cnt++;
            });
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


// 재고 조정 : 재고 목록 checkbox 선택
let selectedNumber
$('.inventory-checkbox').on('change', function () {
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
    let currentValue = parseInt($('#adjustment-cnt').val());
    let minValue = 1;
    let maxValue = selectCnt;
    console.log(selectCnt)

    if ($("#out-bound").prop("checked")) {
        if (currentValue > maxValue) {
            toastr.warning('기존 수량보다 많이 입력할 수 없습니다.');
        } else if (currentValue < minValue) {
            toastr.warning('1 이상의 수량을 입력해야 합니다.');
        }
    } else {
        if (currentValue < minValue) {
            toastr.warning('1 이상의 수량을 입력해야 합니다.');
        }
    }
}

$('#adjustment-cnt').on('input', function () {
    handleOutputQuantityChange();
});


// 재고 조정 버튼 클릭 시 반영
$('#submitButton').on('click', function () {

    let inBoundChecked = $("#in-bound").prop("checked");
    let outBoundChecked = $("#out-bound").prop("checked");

    if (!inBoundChecked && !outBoundChecked) {
        toastr.warning('조정 구분을 선택하세요.')
    } else if (isNaN(parseFloat($('#adjustment-cnt').val()))) {
        toastr.warning('수량을 입력하세요.')
    } else if (isNaN(selectCnt)) {
        toastr.warning('재고목록을 선택해주세요.')
    } else {
        let quantity = parseFloat($('#adjustment-cnt').val());
        let type = $('#in-bound').prop('checked') ? 'CHANGE_CNT_INBOUND' : 'CHANGE_CNT_OUTBOUND';

        let data = {
            pkInventorySeq: selectedNumber,
            vInventoryChangeType: type,
            nInventoryShippingCnt: quantity
        };

        console.log(data);

        $.ajax({
            url: '/inventory/adjustment',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                console.log('서버 응답:' + JSON.stringify(data));
                toastr.success('재고 수량 조정 완료');
                window.scrollTo(0, 0);
                reloadPageAfterDelay(1500);
            },
            error: function (xhr, status, error) {
                console.error('에러 발생:', error);
                toastr.error('재고 수량 조정 실패');
                window.scrollTo(0, 0);
                reloadPageAfterDelay(1500);
            }
        });
    }
});


function reloadPageAfterDelay(delay) {
    setTimeout(function () {
        location.reload();
    }, delay);
}


// 3. 재고 이동
$('#submitMovementButton').on('click', function () {
    let warehouse = $('#select-warehouse').val();
    let zone = $('#select-zone').val();

    let data = {
        pkInventorySeq: selectedNumber,
        vWarehouseNm2: warehouse,
        vZoneNm2: zone
    }

    console.log(data)

    $.ajax({
        url: '/inventory/movement',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            console.log('서버 응답:' + JSON.stringify(data));
            toastr.success('창고 및 구역 이동 완료')
            window.scrollTo(0, 0);
            reloadPageAfterDelay(1000);
        },
        error: function (xhr, status, error) {
            console.error('에러 발생:', error);
            toastr.error('창고 및 구역 이동 실패')
            window.scrollTo(0, 0);
            reloadPageAfterDelay(1000);
        }
    });
})
;
