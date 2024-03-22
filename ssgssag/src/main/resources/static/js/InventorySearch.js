$(document).ready(function () {
    $("#search-button").click(function () {
        readInventory();
    });
});

var InventorySearchForm = {
    "vMainCategoryNm": null,
    "vSubCategoryNm": null,
    "vDetailCategoryNm": null,
    "vWarehouseNm": null,
    "vZoneNm": null
}

function inventoryReadPageReset() {
    console.log('초기화')
    location.reload()
}

function searchForm() {

    let mainCategory = $("#select-main-category").val();
    if (mainCategory !== '대분류')
        InventorySearchForm.vMainCategoryNm = mainCategory;

    let subCategory = $("#select-sub-category").val();
    if (subCategory !== '중분류')
        InventorySearchForm.vSubCategoryNm = subCategory;

    let detailCategory = $("#select-detail-category").val();
    if (detailCategory !== '소분류')
        InventorySearchForm.vDetailCategoryNm = detailCategory;

    let wareHouse = $("#select-warehouse").val();
    if (wareHouse !== '창고')
        InventorySearchForm.vWarehouseNm = wareHouse;

    let zone = $("#select-zone").val();
    if (zone !== '구역')
        InventorySearchForm.vZoneNm = zone;

    console.log("검색 폼 " + JSON.stringify(InventorySearchForm));
}

function clearSearchForm() {
    InventorySearchForm.vZoneNm = null;
    InventorySearchForm.vWarehouseNm = null;
    InventorySearchForm.vDetailCategoryNm = null;
    InventorySearchForm.vSubCategoryNm = null;
    InventorySearchForm.vMainCategoryNm = null;
}

function readInventory() {
    console.log("on click 이벤트 출력")

    searchForm()

    $.ajax({
        url: '/inventory/search',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(InventorySearchForm),
        success: function (resp) {

            console.log("호출 " + JSON.stringify(InventorySearchForm))

            // 기존 테이블 본문 제거
            $(".inventory-list-table-body").empty();

            // 새로운 테이블 본문 생성
            let newTableBody = $('.inventory-list-table-body');
            let cnt = 0;

            // 테이블에 데이터 추가
            $.each(resp, function (index, inventory) {

                console.log(inventory)

                let formattedDate = formatDate(inventory.dtInventorySlipDate);
                let row = `<tr role="row" class="odd inventory-detail-link" data-inventory-id="${inventory.pkInventorySeq}">
                                <td data-toggle="modal" data-target="#inventory-history-modal">
                                    <span>${inventory.pkInventorySeq}</span>
                                </td>
                                <td>${formattedDate}</td>
                                <td>${inventory.vproductCd}</td>
                                <td>${inventory.vproductNm}</td>
                                <td>${inventory.ninventoryCnt}</td>
                                <td>${inventory.vwarehouseCd}</td>
                                <td>${inventory.vwarehouseNm}</td>
                                <td>${inventory.vzoneCd}</td>
                            </tr>`;
                newTableBody.append(row);
                cnt = index + 1;
                console.log(row, cnt);
            });

            // 모달 열기
            $('.inventory-detail-link').on('click', function () {
                var inventoryId = $(this).attr('data-inventory-id');
                handleInventoryDetailLinkClick(inventoryId);
            });


            //페이징 처리
            $('#DataTables_Table_0_info').text(`Showing 1 to 10 of ${cnt} entries`);
            $('.pagination').find('.paginate_button').not('#DataTables_Table_0_previous, #DataTables_Table_0_next').remove(); // 이전(pre)과 다음(next)을 제외한 리스트들을 모두 지운다.

            let show = +$('.form-control.form-control-sm').val();
            let page = +Math.max(Math.ceil(cnt / show), 1);

            console.log("page", show, page);

            for (let i = 1; i <= page; i++) {
                console.log('실행');
                $('#DataTables_Table_0_previous').after(
                    `
                    <li class="paginate_button page-item active">
                        <a href="#" aria-controls='DataTables_Table_${i}'
                            data-dt-idx="${i}" tabindex="${i}" class="page-link">${i}</a>
                    </li>
                    `
                );
            }

            // 새로운 테이블 본문을 테이블에 추가
            $('.inventory-list-table').append(newTableBody);
            clearSearchForm();
        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
}


function formatDate(dateString) {
    if (!dateString) {
        return '';
    }
    let date = new Date(dateString);
    let year = date.getFullYear();
    let month = ('0' + (date.getMonth() + 1)).slice(-2);
    let day = ('0' + date.getDate()).slice(-2);
    return year + '-' + month + '-' + day;
}