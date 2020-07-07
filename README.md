# process-file
	Serviço para realizar o processamento de arquivos.

# Tecnologias

	Projeto Sprint Boot
	Apache Camel
	Java 8
	Maven

# Requisitos
    Possuir o java 8 instalado, nesse projeto foi realizado o uso do jdk 1.8.0_251
    
# Orientações

	Os arquivos que seram processados pela aplicação deverão ficar em ${HOMEPATH}/data/in/
	com a extenção .dat
	
	Os dados do arquivo devem estar corretos para o que o arquivo seja processado
	
	A saída do processamento vai estar em ${HOMEPATH}/data/out/ com a extenção done.dat

# Executar

	Acesse a pasta target dentro do projeto e executar o jar process-file-0.0.1-SNAPSHOT com java -jar 

# Compilação

	Para compilar esse projeto é necessário ter instalado o jdk1.8.0_251