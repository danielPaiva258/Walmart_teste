# Walmart

Como Configurar:

-Baixe o projeto atraves do site GitHub no formato .zip
-Extraia o projeto
-Importe o projeto no eclipse IDE : File->Import->Import Existing Projects into Workspace
-Click com o botão direito no projeto selecione maven->Update Project
-Para persistir os dados foi utilizado o MySql, é necessario que o MySQL esteja instalado na máquina (http://matheuspiscioneri.com.br/blog/instalacao-do-mysql-windows-7-64-bits/)
-Após instalado o mysql, é necessário criar um banco de dados com o nome "walmart"
-No projeto, na pasta java resources /src/main/resources/META-INF é preciso editar o arquivo persistence.xml
-substitua os atributos value das tags:
<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost/walmart" />
<property name="javax.persistence.jdbc.user" value="root" />
<property name="javax.persistence.jdbc.password" value="root" />

onde a primeira é a url de conexão com o banco de dados (normalmente não precisa alterar)
a segunda é o usuário que foi configurado na instalação
a terceira é a senha configurada(se nenhuma senha foi configurada deixar como value=""

-Instale e configure o tomcat 7.0 em seu eclipse (pode ser visto no tutorial https://www.youtube.com/watch?v=h_Ab1H7n11o)
-clique com o botão direito no tomcat na aba servers e selecione add and remove
-Adicione o projeto ao tomcat
-Execute o tomcat e veja se está tudo ok
- Se tudo foi configurado corretamente ao executar o tomcat o hibernate irá gerar as tabelas no banco de dados criado

- A tela inicial do projeto pode ser acessada na url http://localhost:8080/Walmart/cadastroMapa.xhtml (onde 8080 é a porta padrão tomcat, se configurou outra então alterar)

OBS: Os nomes de mapas e cidades devem ser digitados exatamente como foram cadastrados, levando em conta letras maiusculas, minusculas e espaços.

Utilizei as tecnologias JSF, Hibernate e java pela praticidade e facilidade de configuração e integração entre esses recursos. O JSF
dispõe de componentes extremamente faceis de serem configurados, ajudando a montar uma interface simples e bonita. O java possui ínumeros
recursos de Collections para serem utilizados, facilitando a implementação do algoritmo de Dijkstra que foi utilizado para calcular as rotas próximas.
