// 페이지가 로드될 때 실행되는 함수
$(document).ready(function() {
    console.log("실행");
    getWarehouseAndZone();
    getCategoryHierarchy();
});

// 카테고리 입력폼
$('#select-main-category').change(function() {
    let $subCategory = $('#select-sub-category');
    $subCategory.empty();
    $subCategory.append(
        `<option value="중분류">중분류</option>`
    );

    $('#select-detail-category').empty();
    $('#select-detail-category').append(`
       <option value="소분류">소분류</option>`
    );

    if ($('#select-main-category').val()==="대분류")
        return;

    let mainCategory = $('#select-main-category').val(); // 선택된 창고 이름을 가져옴
    renderSubCategory(mainCategory);
});

$('#select-sub-category').change(function() {
    let $detailCategory = $('#select-detail-category');
    $detailCategory.empty();
    $detailCategory.append(
        `<option value="소분류">소분류</option>`
    );
    let mainCategory = $('#select-main-category').val();
    let subCategory = $('#select-sub-category').val();

    if (subCategory==="중분류")
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
        success: function(data) {
            // Warehouse 이름을 key로, Zone 이름들의 배열을 value로 하는 객체
            let categoryHierarchy = {};

            data.forEach(function(item) {
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
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}


function renderMainCategory() {
    console.log("render 호출")
    // warehouseMap의 키를 가져옴
    let keys = Array.from(categoryMap.keys());

    // 각 창고명을 select 요소에 옵션으로 추가
    keys.forEach(function(category) {
        $('#select-main-category').append(
            `<option value="${category}">${category}</option>`
        );
    });
}

function renderSubCategory(mainCategory) {
    console.log("render 호출")

    let mainCategoryMap = categoryMap.get(mainCategory);
    let keys = Array.from(mainCategoryMap.keys());

    // 각 창고명을 select 요소에 옵션으로 추가
    keys.forEach(function(category) {
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


    // 각 창고명을 select 요소에 옵션으로 추가
    keys.forEach(function(category) {
        $('#select-detail-category').append(
            `<option value="${category}">${category}</option>`
        );
    });
}


// 창고 입력폼

$('#select-warehouse').change(function() {
    let $zone = $('#select-zone');
    $zone.empty();
    $zone.append(
        `<option value="구역">구역</option>`
    );
    let warehouse = $('#select-warehouse').val(); // 선택된 창고 이름을 가져옴
    if (warehouse==="창고")
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
        success: function(data) {
            // Warehouse 이름을 key로, Zone 이름들의 배열을 value로 하는 객체
            let warehouseZones = {};

            // 데이터를 처리하여 Map 객체와 객체를 채움
            $.each(data, function (idx, ware) {
                let warehouseName = ware.vwarehouseNm;
                let zoneName = ware.vzoneNm;

                // Map 객체에 데이터 추가
                if (!warehouseMap.has(warehouseName)) {
                    warehouseMap.set(warehouseName, []);
                }
                warehouseMap.get(warehouseName).push(zoneName);

                // 객체에 데이터 추가
                if (!warehouseZones[warehouseName]) {
                    warehouseZones[warehouseName] = [];
                }
                warehouseZones[warehouseName].push(zoneName);
                console.log("before");

            });

            renderWarehouseNm();
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}


function renderWarehouseNm() {
    // warehouseMap의 키를 가져옴
    let keys = Array.from(warehouseMap.keys());

    // 각 창고명을 select 요소에 옵션으로 추가
    keys.forEach(function(warehouse) {
        $('#select-warehouse').append(
            `<option value="${warehouse}">${warehouse}</option>`
        );
    });
}

// 구역 코드 렌더링
function renderWarehouseZone(warehouseNm) {
    let zoneList = warehouseMap.get(warehouseNm);

    zoneList.forEach(function(zone) {
        $('#select-zone').append(
            `<option value="${zone}">${zone}</option>`
        );
    });

}

// 여기서 함수를 선언하고 조히 버튼에서 onclick으로 달아준다
