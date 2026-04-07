# Plataforma de Integração Fullstack – Varejo & Parceiros

## 📌 Visão Geral
Este projeto simula uma **plataforma de integração fullstack** entre um sistema de varejo e parceiros externos (ex: fidelidade, cashback ou analytics), reproduzindo um cenário real de mercado.

O objetivo é demonstrar conceitos de **integração via APIs**, processamento de dados transacionados, monitoramento de status, análise de logs e **documentação técnica clara**, alinhados a ambientes corporativos.

---

## 🎯 Objetivos do Projeto
- Simular integrações entre sistemas internos e parceiros externos
- Centralizar o acompanhamento de status das integrações
- Disponibilizar logs técnicos para análise e troubleshooting
- Demonstrar boas práticas de documentação e versionamento

---

## 🧩 Arquitetura da Solução
A solução é composta por dois módulos principais:

### 🔹 Backend
Responsável por:
- Receber dados de compras do varejo
- Validar e persistir informações
- Enviar dados para um parceiro externo (mock)
- Registrar status e logs das integrações
- Disponibilizar APIs para consulta

### 🔹 Frontend
Responsável por:
- Exibir dashboard com status das integrações
- Listar transações processadas
- Permitir consulta de logs e falhas

---

## 🛠️ Tecnologias Utilizadas

### Backend
- Java
- Spring Boot
- APIs REST
- MySQL
- Autenticação via Token/JWT
- Logs estruturados

### Frontend
- Angular ou React
- Consumo de APIs REST
- Interface administrativa

### Documentação & Versionamento
- Markdown
- Swagger (OpenAPI)
- Git

---

## 🔌 Fluxo de Integração
1. O sistema de varejo envia uma compra para a API  
2. A API valida os dados recebidos  
3. A compra é persistida no banco de dados  
4. O serviço de integração envia os dados ao parceiro externo (mock)  
5. O status da integração é registrado (SUCESSO ou ERRO)  
6. Logs técnicos são armazenados para análise  
7. O frontend consome as APIs para exibição dos dados  

---

## 📡 Endpoints Principais

### Criar Compra
`POST /purchases`

**Request (exemplo):**
```json
{
  "purchaseId": "12345",
  "customerId": "78910",
  "totalAmount": 199.90,
  "date": "2026-01-20"
}