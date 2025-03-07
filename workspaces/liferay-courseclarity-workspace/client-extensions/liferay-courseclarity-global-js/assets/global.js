document.addEventListener("DOMContentLoaded", function () {
    const defaultFontSize = 16; // Default font size in px
    let currentFontSize = defaultFontSize;

    // Create control panel
    const controlPanel = document.createElement("div");
    controlPanel.id = "accessibility-control-panel";
    controlPanel.style.position = "fixed";
    controlPanel.style.top = "20px"; // Top right position
    controlPanel.style.right = "20px";
    controlPanel.style.backgroundColor = "transparent";
    controlPanel.style.border = "none";
    controlPanel.style.padding = "5px";
    controlPanel.style.zIndex = "9999";
    controlPanel.style.fontFamily = "Arial, sans-serif";
    controlPanel.style.display = "flex";
    controlPanel.style.gap = "5px";
    controlPanel.style.alignItems = "center";

    // Create buttons A+ and A-
    const increaseButton = document.createElement("button");
    increaseButton.textContent = "A+";
    increaseButton.style.padding = "5px 10px";
    increaseButton.style.border = "1px solid #1e6f6f";
    increaseButton.style.backgroundColor = "#1e6f6f";
    increaseButton.style.color = "#fff";
    increaseButton.style.borderRadius = "4px";
    increaseButton.style.cursor = "pointer";
    increaseButton.style.fontSize = "12px";

    const decreaseButton = document.createElement("button");
    decreaseButton.textContent = "A-";
    decreaseButton.style.padding = "5px 10px";
    decreaseButton.style.border = "1px solid #1e6f6f";
    decreaseButton.style.backgroundColor = "#1e6f6f";
    decreaseButton.style.color = "#fff";
    decreaseButton.style.borderRadius = "4px";
    decreaseButton.style.cursor = "pointer";
    decreaseButton.style.fontSize = "12px";

    increaseButton.addEventListener("click", function () {
        if (currentFontSize < 24) { // Limit font size to a maximum
            currentFontSize += 2;
            document.documentElement.style.fontSize = `${currentFontSize}px`;
        }
    });

    decreaseButton.addEventListener("click", function () {
        if (currentFontSize > 12) { // Limit font size to a minimum
            currentFontSize -= 2;
            document.documentElement.style.fontSize = `${currentFontSize}px`;
        }
    });

    // Grayscale toggle
    const grayscaleToggle = document.createElement("div");
    grayscaleToggle.style.width = "20px";
    grayscaleToggle.style.height = "20px";
    grayscaleToggle.style.borderRadius = "50%";
    grayscaleToggle.style.background = "linear-gradient(to right, black 50%, white 50%)";
    grayscaleToggle.style.cursor = "pointer";
    grayscaleToggle.style.border = "1px solid #ccc";

    grayscaleToggle.addEventListener("click", function (event) {
        const clickX = event.offsetX;
        const toggleWidth = grayscaleToggle.offsetWidth;

        if (clickX < toggleWidth / 2) {
            // Left side (black): enable grayscale
            document.documentElement.style.filter = "grayscale(100%)";
        } else {
            // Right side (white): disable grayscale
            document.documentElement.style.filter = "none";
        }
    });

    // Append elements to panel
    controlPanel.appendChild(decreaseButton);
    controlPanel.appendChild(increaseButton);
    controlPanel.appendChild(grayscaleToggle);

    // Append panel to body
    document.body.appendChild(controlPanel);
});
