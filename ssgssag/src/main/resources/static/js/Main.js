document.addEventListener('DOMContentLoaded', function () {
  const slider = document.querySelector('.scrollable-row-wrapper');
  const flipButton = document.querySelector('#flip-card .btn-dark');
  const backToCategoryChartButton = document.getElementById('back-to-best-category-chart');
  const cardInner = document.querySelector('#flip-card-inner');

  let isDown = false;
  let startX;
  let scrollLeft;

  slider.addEventListener('mousedown', (e
  ) => {
    isDown = true;
    slider.classList.add('active');
    startX = e.pageX - slider.offsetLeft;
    scrollLeft = slider.scrollLeft;
    slider.style.cursor = 'grabbing';
  });

  slider.addEventListener('mouseleave', () => {
    isDown = false;
    slider.style.cursor = 'grab';
  });

  slider.addEventListener('mouseup', () => {
    isDown = false;
    slider.style.cursor = 'grab';
  });

  slider.addEventListener('mousemove', (e) => {
    if (!isDown) return;
    e.preventDefault();
    const x = e.pageX - slider.offsetLeft;
    const walk = (x - startX) * 2;
    slider.scrollLeft = scrollLeft - walk;
  });

  flipButton.addEventListener('click', function() {
    cardInner.classList.toggle('flip'); // 'flip' 클래스 토글로 뒤집기 효과 적용
  });
  backToCategoryChartButton.addEventListener('click', function() {
    cardInner.classList.toggle('flip');
  });



  const flipButtonWorst = document.querySelector('#flip-card-worst .btn-primary');
  const backToWorstCategoryChartButton = document.getElementById('back-to-worst-category-chart');
  const cardInnerWorst = document.querySelector('#flip-card-inner-worst');

  flipButtonWorst.addEventListener('click', function() {
    cardInnerWorst.classList.toggle('flip'); // 'flip' 클래스 토글로 뒤집기 효과 적용
  });
  backToWorstCategoryChartButton.addEventListener('click', function() {
    cardInnerWorst.classList.toggle('flip');
  });
});

