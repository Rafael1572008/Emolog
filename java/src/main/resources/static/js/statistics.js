// statistics.js

// Dashboard
const coresHumor = { 5: "#10b981", 4: "#3b82f6", 3: "#fbbf24", 2: "#f97316", 1: "#ef4444" };
const nomesHumor = { 5: "Radiante", 4: "Bem", 3: "Médio", 2: "Mal", 1: "Horrível" };

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch("http://localhost:8080/humor/diario");
        if (!response.ok) throw new Error("Erro na API");

        const dados = await response.json();
        const ctx = document.getElementById("graficoHumor").getContext("2d");

        // Gradiente suave e elegante
        const gradient = ctx.createLinearGradient(0, 0, 0, 450);
        gradient.addColorStop(0, "rgba(99, 102, 241, 0.25)");
        gradient.addColorStop(1, "rgba(99, 102, 241, 0)");

        new Chart(ctx, {
            type: "line",
            data: {
                labels: dados.map(d => d.data),
                datasets: [{
                    data: dados.map(d => d.valor),
                    borderColor: "#a78bfa",
                    backgroundColor: gradient,
                    borderWidth: 3,
                    pointRadius: 9,
                    pointHoverRadius: 12,
                    pointBackgroundColor: dados.map(d => coresHumor[d.valor]),
                    pointBorderColor: "#000",
                    pointBorderWidth: 2,
                    pointHoverBorderWidth: 3,
                    tension: 0.5,
                    fill: true
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    tooltip: {
                        backgroundColor: "rgba(15,15,15,0.95)",
                        titleColor: "#fff",
                        bodyColor: "#ddd",
                        borderColor: "#333",
                        borderWidth: 1,
                        cornerRadius: 12,
                        padding: 14,
                        displayColors: false,
                        titleFont: { size: 14, weight: "normal" },
                        bodyFont: { size: 16 },
                        callbacks: {
                            label: ctx => `${nomesHumor[ctx.parsed.y]} • ${ctx.parsed.y}/5`,
                            afterLabel: ctx => `${dados[ctx.dataIndex].registrosNoDia} registro${dados[ctx.dataIndex].registrosNoDia > 1 ? "s" : ""}`
                        }
                    }
                },
                scales: {
                    x: {
                        ticks: { color: "#777", font: { size: 12 } },
                        grid: { color: "rgba(255,255,255,0.03)" }
                    },
                    y: {
                        min: 0.5,
                        max: 5.5,
                        ticks: {
                            stepSize: 1,
                            color: "#777",
                            font: { size: 12 },
                            callback: v => nomesHumor[v] || ""
                        },
                        grid: { color: "rgba(255,255,255,0.03)" }
                    }
                },
                interaction: { intersect: false, mode: "index" }
            }
        });

    } catch (e) {
        console.error(e);
        document.body.innerHTML += '<p style="text-align:center;color:#f87171;margin-top:3rem;">Erro ao carregar os dados</p>';
    }
});

// Calendário

let currentDate = new Date();
const calendarGrid = document.getElementById("calendarGrid");
const calendarTitle = document.getElementById("calendarTitle");
let dadosHumor = []; // Preenchido pela API acima

const faces = {
    1: "☹️",
    2: "😕",
    3: "🙂",
    4: "😊",
    5: "😄"
};

function carregarCalendario() {
    const ano = currentDate.getFullYear();
    const mes = currentDate.getMonth();

    calendarTitle.textContent =
        currentDate.toLocaleString("pt-BR", { month: "long" }) + " " + ano;

    calendarGrid.innerHTML = "";

    const nomesDias = ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"];
    nomesDias.forEach(n => {
        const div = document.createElement("div");
        div.textContent = n;
        div.classList.add("day-name");
        calendarGrid.appendChild(div);
    });

    const primeiroDiaSemana = new Date(ano, mes, 1).getDay();
    const diasNoMes = new Date(ano, mes + 1, 0).getDate();

    for (let i = 0; i < primeiroDiaSemana; i++) {
        const empty = document.createElement("div");
        empty.classList.add("calendar-empty");
        calendarGrid.appendChild(empty);
    }

    for (let dia = 1; dia <= diasNoMes; dia++) {
        const div = document.createElement("div");

        const dataISO = `${ano}-${String(mes+1).padStart(2,"0")}-${String(dia).padStart(2,"0")}`;

        const humorDia = dadosHumor.find(d => d.data === dataISO);

        div.classList.add("calendar-day");

        if (humorDia) {
            div.classList.add(`mood-${humorDia.valor}`);
            div.innerHTML = `<span class='mood-face'>${faces[humorDia.valor]}</span>`;
        } else {
            div.style.opacity = "0.25";
            div.textContent = dia;
        }

        calendarGrid.appendChild(div);
    }
}

document.getElementById("prevMonth").addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    carregarCalendario();
});

document.getElementById("nextMonth").addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    carregarCalendario();
});

document.addEventListener("DOMContentLoaded", async () => {
    const resp = await fetch("http://localhost:8080/humor/diario");
    dadosHumor = await resp.json();
    carregarCalendario();
});