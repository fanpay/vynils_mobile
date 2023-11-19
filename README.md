# MISW4203 - Vynils Mobile - Grupo8

Vinyls es una aplicación que permite la visualización y gestión de información relacionada con música, como por ejemplo álbumes, artistas, coleccionistas	, entre otros. Para la versión móvil se usará una API REST que está previamente desarrollada y es muy similar a la API que estamos usando en la versión web de Vinilos.

Puede encontrar más información sobre el proyecto en la wiki 
* [Vynils Mobile](https://github.com/fanpay/vynils_mobile/wiki)

## Instrucciones para construir la aplicación de forma local

1. Tener instalado Android Studio
   * [Guía](https://misovirtual.virtual.uniandes.edu.co/codelabs/android-setup-tutorial/index.html#0)
2. Clonar el repositorio
4. Abrir el proyecto con Android Studio
5. Sincronizar el archivo build.gradle para configurar las versiones de los sistemas de su máquina
6. Ejecutar la aplicación mediante alguna de las siguientes formas:
    1. **Ejecución en un celular:** Descargue en su celular el archivo `app-debug.apk` e instálelo . Una vez instalado, asegurese de tener conexión a internet para ejecutar la aplicación. Puede encontrar el archivo en las siguientes ubicaciones:
        1. En el repositorio: `release_apk/app-debug.apk`
        2. El el siguiente enlace: [app-debug.apk](https://uniandes-my.sharepoint.com/:u:/g/personal/s_mascareno_uniandes_edu_co/ERu-soUNA9dJppa4VSI_-sgBWdG_wpP3F74fbfAmU669Dw?e=BO6RXY)
    2. **Ejecución en el emulador de Android Studio:** Puede ejecutar la aplicación desde un emulador Android desde Android Studio. Solo debe crearlo, seleccionarlo y ejecutarlo.
       * [Instrucciones](https://developer.android.com/studio/install?hl=es-419)
    4. **Emulación de la aplicación en un celular:** Puede usar su dispositivo Android como emulador y ejecutar la aplicación desde su máquina.
       * [Instrucciones](https://developer.android.com/training/basics/firstapp/running-app?hl=es-419)
    
## Instrucciones para ejecutar las pruebas E2E en Espresso

1. Desde Android Studio se deben ejecutar los archivos existentes en la carpeta: **app/src/androidTest/java/com.uniandes.vynilsmobile/**
Cada archivo de esta ubicación representa un escenario de pruebas 
2. Hacer click derecho en cada archivo de pruebas
3. Hacer click en **Run 'NombreArchivo'**
4. Finalmente se puede visualizar la ejecución exitosa de las pruebas como la siguiente imagen:
    <img width="500" alt="image" src="https://github.com/fanpay/vynils_mobile/assets/20799651/91011c58-0466-4c63-ae89-b43a4ced13a3">


## Ver release asociado a una versión.
Para visualizar el release asociado a una versión debe dar click en la lista de branches y seleccionar `Tags`. De esta manera puede visualizar los cambios correspondientes a la iteración. Note que en la parte derecha se encuentra la relación de la versión al release.
