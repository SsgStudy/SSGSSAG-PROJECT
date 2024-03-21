// 페이지가 로드될 때 실행되는 함수
$(document).ready(function() {
    console.log("실행");
    getWarehouseAndZone();
    // AJAX 요청을 보냄
    // $.ajax({
    //     url: '/inventory/list',
    //     type: 'GET',
    //     dataType: 'json',
    //     success: function(data) {
    //         // 받아온 JSON 데이터에서 warehouse 목록 추출
    //         var wareHouseZoneList = data.wareHouseZoneList;
    //
    //         // 중복을 제거한 창고(warehouse) 값 추출
    //         var uniqueWarehouses = [...new Set(wareHouseZoneList.map(data => data.warehouse))];
    //
    //         // Drop down을 만들기 위한 select 요소 가져오기
    //         var selectElement = $('#wareHouseSelect');
    //
    //         // 기존에 select 요소에 있는 옵션 제거
    //         selectElement.empty();
    //
    //         // 선택 옵션 추가
    //         selectElement.append('<option value="" disabled selected>Select Warehouse</option>');
    //
    //         // 중복을 제거한 창고(warehouse) 값을 반복하여 select 요소에 옵션으로 추가
    //         uniqueWarehouses.forEach(function(warehouse) {
    //             selectElement.append($('<option>', {
    //                 value: warehouse,
    //                 text: warehouse
    //             }));
    //         });
    //     },
    //     error: function(xhr, status, error) {
    //         console.error('Error fetching data:', error);
    //     }
    // });
});


$('#select-warehouse').change(function() {
    $('#select-zone').empty();
    $('#select-zone').append(
        `<option value="구역">구역</option>`
    );
    let warehouse = $('#select-warehouse').val(); // 선택된 창고 이름을 가져옴
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
    var keys = Array.from(warehouseMap.keys());

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