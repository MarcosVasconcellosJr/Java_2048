# Manual PTransporte

**1.Setup** 

* API: .NET Framework 4.6.1, para mais informações sobre [Download](https://www.microsoft.com/pt-br/download/details.aspx?id=49982).
* Nuget: como gerenciador de pacotes é utilizado NuGet, mais [informações ](https://www.nuget.org/).

**2- Baixar dependências**

* Todas as dependências do projeto são gerenciadas pelo nuget, para que ele restaure manualmente as bibliotecas: 
 `Update-Package  -reinstall`, para execução deste comando é indicado utilizar o terminal "Package Manager Console" do próprio visual studio

**3- Banco de dados**

* O banco de dados é SQL Server, para conexão utilizamos o ORM [Entity Framework](https://docs.microsoft.com/pt-br/ef/) 

**4- Rodar projeto**

* Utilizando o Visual Studio apenas rode a aplicação 

# Servidor
* OS: Windows Server 2012 ou superior
* Servidor Http: [IIS](https://www.iis.net/) 8 ou superior
* Banco de dados: [SQL Server](https://docs.microsoft.com/pt-br/sql/) 2008 ou superior