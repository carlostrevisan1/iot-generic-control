# iot-generic-control

Dev's: André Salera Ribeiro, Carlos Trevisan, Felipe Bis, Ian Degaspari.

Aplicativo desenvolvido para a disciplina de desenvolvimento Mobile do curso de engenharia de computação do IFSP, o objetivo do aplicativo é controlar sistemas integrados com microcontroladores, através de diversos botões criados pelo próprios usuário, que mandam um valor também definido pelo usuário utilizando uma conexão MQTT, que é um protocolo de conexão leve e eficiente tornando-o ideal para utilizar em comunicação máquina-máquina,, um aplicativo bem nicho para entusiastas da area de IOT, conhecido também como internet das coisas.

Ao iniciar o aplicativo pela primeira vez, temos apenas a opção de cadastrar um novo dispositivo, como podemos ver abaixo:

![alt text](https://github.com/carlostrevisan1/iot-generic-control/raw/master/imagens_read/tela_inicial.jpg "Logo Title Text 1")  ![alt text](https://github.com/carlostrevisan1/iot-generic-control/raw/master/imagens_read/add_device.jpg "Logo Title Text 1")


Após inserir as informações sobre o aplicativo e os dados necessários para efetuar a conexão MQTT podemos entrar em sua pagina e adicionar funcionalidades como botões, sliders, color pickers, togglebuttons e inputs de texto:

![alt text](https://github.com/carlostrevisan1/iot-generic-control/raw/master/imagens_read/escolha_botoes.jpg "Logo Title Text 1")

Ao inserir as funcionalidades, escolhemos o sinal que será enviado ao dispositivo conectado, permitindo o controle de funcionalidades a partir do aplicativo de forma simples e prática no campo "value":

![alt text](https://github.com/carlostrevisan1/iot-generic-control/raw/master/imagens_read/sinal.jpg "Logo Title Text 1")

Abaixo podemos ver um dispositivo com varias funcionalidades cadastradas:

![alt text](https://github.com/carlostrevisan1/iot-generic-control/raw/master/imagens_read/botoes_cadastrados.jpg "Logo Title Text 1")
