function previewImage(event) {
    const imagePreview = document.getElementById('imagePreview');
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function(e) {
        imagePreview.innerHTML = '<img src="' + e.target.result + '" alt="Uploaded Image">';
    }

    if (file) {
        reader.readAsDataURL(file);
    }
}

document.getElementById('imageUpload').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const formData = new FormData();
        formData.append('imageUrl', file);

        fetch('/v1/api/community/upload-image', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(data => {
                // 画像アップロード後の処理
                const imagePreview = document.getElementById('imagePreview');
                imagePreview.innerHTML = `<img src="${data}" alt="Uploaded Image">`;
            })
            .catch(error => {
                console.error('Error uploading image:', error);
            });
    }
});
