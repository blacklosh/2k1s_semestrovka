$(document).ready(function () {
    let form = $("#form")
    let postsList = $("#pack-list")
    form.on('submit', function () {
        let content = form.find("#content").val();
        let taskid = form.find("#taskid").val();
        if (content === '') {
            return false
        }
        form.find("#content").val("--отправлено на проверку--")
        $.ajax("/compile", {
            method: "POST",
            data: {
                "code":content,
                "taskid":taskid
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (data) {
                let p = JSON.parse(data)
                form.find("#content").val("")
                let postTag = $("<tr>")
                postTag.append("<td>"+p.id+"</td>")
                postTag.append("<td>"+p.date+"</td>")
                postTag.append("<td>"+p.lang+"</td>")
                postTag.append("<td>"+p.result+"</td>")
                postTag.append("<td>"+p.message+"</td>")
                postTag.append("<td>"+p.score+"</td>")
                postTag.append("<td><a href='/viewCode?packid="+p.id+"'>Просмотреть код</a></td>")
                postTag.append("</tr>")
                postTag.hide()
                postsList.append(postTag)
                postTag.show(300)
            }
        })
        return false
    })
})