function carregarAgenda() {
    var instrutorId = $("#instrutorSelect").val();

    // Fazer uma requisição AJAX para buscar os dados da agenda do instrutor
    $.ajax({
        url: "/instrutor/listInstrutor/" + instrutorId,
        method: "GET",
        success: function (data) {
            atualizarGrid(data);
        },
        error: function (error) {
            console.error(error);
        }
    });
}
function salvarAula(button) {
    var instrutorId = $("#instrutorSelect").val();
    var alunoId = $(button).val();
    var horario = $(button).data("horario");
    var dia = $(button).data("dia");

    // Verifique se os valores do instrutor, aluno, horário e dia foram selecionados
    if (instrutorId && alunoId && horario && dia) {
        // Concatene os dados em uma única string
        var dadosAula = alunoId + "-" + dia + "-" + horario + "-" + instrutorId;

        // Faça uma requisição AJAX para salvar os dados da aula no servidor
        $.ajax({
            url: "/api/agendamento",
            method: "POST",
            data: { dadosAula: dadosAula },
            success: function (response) {
                // A aula foi salva com sucesso
                console.log("Aula salva no banco de dados.");
            },
            error: function (error) {
                // Ocorreu um erro ao salvar a aula
                console.error("Erro ao salvar aula: " + error);
            }
        });
    } else {
        // Verifique qual valor não foi selecionado
        if (!instrutorId) {
            console.error("Selecione um instrutor.");
        }
        if (!alunoId) {
            console.error("Selecione um aluno.");
        }
        if (!horario) {
            console.error("Selecione um horário.");
        }
        if (!dia) {
            console.error("Selecione um dia.");
        }
    }
}
function atualizarGrid(data) {
    // Limpar o grid
    $("table tbody").empty();

    // Percorrer os dados e preencher o grid
    data.forEach(function (item) {
        var tr = $("<tr></tr>");
        tr.append("<td>" + item.horario + "</td>");

        var dias = ["Segunda", "Terça", "Quarta", "Quinta", "Sexta"];
        dias.forEach(function (dia) {
            var td = $("<td></td>");

            // Verificar se existe um aluno marcado para esse horário e dia
            var aluno = item.alunos.find(function (aluno) {
                return aluno.dia === dia;
            });

            if (aluno) {
                td.append('<button class="btn btn-info" data-horario="' + item.horario + '" data-dia="' + dia + '" onclick="abrirModal(this)">' + aluno.nome + '</button>');
            } else {
                td.append('<button class="btn btn-primary" data-horario="' + item.horario + '" data-dia="' + dia + '" onclick="abrirModal(this)">+</button>');
            }

            tr.append(td);
        });

        $("table tbody").append(tr);
    });
}

function abrirModal(button) {
    let horario = $(button).data("horario");
    let dia = $(button).data("dia");

    // Limpar o select de alunos e carregar os alunos disponíveis para seleção
    $("#alunoSelect").empty();
    carregarAlunos($(button)); // Função para carregar os alunos disponíveis

    // Armazenar os dados do horário e dia no botão
    $(button).data("horario", horario);
    $(button).data("dia", dia);

    // Abrir o modal
    $("#modal").modal("show");
}

function adicionarAluno() {
    var horario = $(button).data("horario");
    var dia = $(button).data("dia");
    var alunoId = $("#alunoSelect").val();

    // Fazer uma requisição AJAX para enviar os dados do aluno e horário selecionados
    $.ajax({
        url: "/api/agendamento",
        method: "POST",
        data: {
            horario: horario,
            dia: dia,
            alunoId: alunoId
        },
        success: function (response) {
            // Atualizar a página ou realizar alguma ação de sucesso
            // Por exemplo, recarregar a agenda do instrutor
            carregarAgenda();

            // Fechar o modal
            $("#modal").modal("hide");
        },
        error: function (error) {
            console.error(error);
        }
    });
}

function carregarAlunos(button) {
    // Fazer uma requisição AJAX para buscar os alunos disponíveis
    $.ajax({
        url: "/viewHomePage",
        method: "GET",
        success: function (data) {
            // Preencher o select de alunos com os nomes dos alunos retornados
            var select = $("<select></select>");
            data.listAlunos.forEach(function (aluno) {
                select.append('<option value="' + aluno.id + '">' + aluno.nome + '</option>');
            });

            // Adicionar o select ao botão
            $(button).append(select);
        },
        error: function (error) {
            console.error(error);
        }
    });
}

$(document).ready(function () {
    carregarAgenda();
});