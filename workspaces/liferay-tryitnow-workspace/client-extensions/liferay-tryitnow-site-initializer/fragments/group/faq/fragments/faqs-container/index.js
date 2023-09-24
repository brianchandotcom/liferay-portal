let length = $('#accordion-container .custom-btn').length;
console.log(length);
for (let i = 0; i < length; i++) {
    let button = $($('#accordion-container .custom-btn a')[i]);
    let p = $($('#accordion-container  .custom-p p')[i]);

    button.attr('class', button.attr('class') + ' btn btn-link collapsed');
    button.attr('data-toggle', 'collapse');
    button.attr('data-target', '#toggle-' + i);
    button.attr('aria-expanded', 'false');
    button.attr('type', 'button');
    button.click(function (e) {
        e.preventDefault();
    });

    p.attr('id', 'toggle-' + i);
    p.attr('class', p.attr('class') + ' collapse');
    p.attr('data-parent', '#accordion-container');
}