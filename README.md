### Ürituste haldamise rakendus

## Kirjeldus

See on väike rakendus, mis tegeleb ürituste haldusega. 
Rakenduse kaudu on võimalik:
* lisada üritusi
* lisada osalejaid üritustele, kes on kas ettevõtted või füüsilised isikud
* muuta ja kustutada üritusi
* vaadata möödunud üritusi
* muuta ja kustutada osalejaid

## Rakenduse käivitamine

Tegemist on Java rakendusega, mis kasutab Springi ja Thymeleafi, et luua frontend ja backend. Rakenduse andmebaas on loodud mysqlis, seega rakendust saab jooksutada ainult kohas, kus mysqli server on töös. Rakenduse käivitamiseks tuleb rakendus Intellij kaudu importida ja mysqli server käivitada. Rakendus saab käivitada jooksutades EventioApplication klassi. Pärast edukat käivitamist avaneb rakendus lingilt: http://localhost:8080

### Ülevaade rakendusest

Rakenduse funktsionaalsus jaguneb kahe suure osa: ürituse ja osavõtja vahel. Backendi failid mõlema osa jaoks on ära jaotatud kaustadesse event ja participant, sisaldes mõlema osa entity, service, controller ja repository faile.
Rakenduse HTML failid on leitavad kaustas /resources/templates ja pildifailid on kaustas /static/images.
Rakendusel on mõned näidistestid, mis asuvad kaustas test/java/com.liis.eventio

