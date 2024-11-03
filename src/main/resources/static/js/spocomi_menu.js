// ハンバーガーメニューの開閉
function toggleMenu() {
    const sideMenu = document.getElementById('side-menu');
    const overlay = document.getElementById('overlay');
    if (sideMenu.style.right === '0px') {
        sideMenu.style.right = '-250px';
        overlay.style.display = 'none';
    } else {
        sideMenu.style.right = '0';
        overlay.style.display = 'block';
    }
}

