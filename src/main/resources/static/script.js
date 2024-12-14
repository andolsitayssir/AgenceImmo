function previewImages() {
    var preview = document.querySelector('#imagePreview');
    var fileInput = document.querySelector('#formFile');
    var files = fileInput.files;
    if (files) {
        [].forEach.call(files, readAndPreview);
    }
    function readAndPreview(file) {
        if (!/\.(jpe?g|png|gif)$/i.test(file.name)) {
            return alert(file.name + " is not an image");
        }
        var reader = new FileReader();
        reader.addEventListener("load", function() {
            var imageWrapper = document.createElement('div');
            imageWrapper.className = 'image-wrapper';
            imageWrapper.style.position = 'relative';
            imageWrapper.style.display = 'inline-block';
            imageWrapper.style.margin = '5px';

            var image = new Image();
            image.height = 100;
            image.title = file.name;
            image.src = this.result;
            imageWrapper.appendChild(image);

            var deleteButton = document.createElement('button');
            deleteButton.innerHTML = 'X';
            deleteButton.className = 'delete-button';
            deleteButton.style.position = 'absolute';
            deleteButton.style.top = '5px';
            deleteButton.style.right = '5px';
            deleteButton.style.backgroundColor = 'red';
            deleteButton.style.color = 'white';
            deleteButton.style.border = 'none';
            deleteButton.style.borderRadius = '50%';
            deleteButton.style.cursor = 'pointer';
            deleteButton.addEventListener('click', function() {
                preview.removeChild(imageWrapper);
                if (preview.children.length === 0) {
                    fileInput.value = "";
                }
            });
            imageWrapper.appendChild(deleteButton);

            preview.appendChild(imageWrapper);
        });
        reader.readAsDataURL(file);
    }
}