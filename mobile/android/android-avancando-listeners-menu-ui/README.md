# Android parte 2: avançando com listeners, menu e UI

Aprendizado do curso [Android parte 2: avançando com listeners, menu e UI](https://cursos.alura.com.br/course/android-avancando-listeners-menu-ui) da Alura.

## Aprendizados

Uma das primeiras coisas que fazia tempo que não utilizava era os métodos `putExtra` e `getExtra` do `Intent`, que são utilizados para passar dados entre as `Activity`. Aprendi a passar dados entre as `Activity` e a utilizar o `Serializable` para passar objetos de maneira serializada.

No curso o [@alexfelipe](https://github.com/alexfelipe) ensinou a utilizar o id de maneira incremental no projeto, na lista de alunos, porém fiz uma pequena alteração, ao invés de utilizar o id incremental, utilizei o `UUID.randomUUID().toString()`, para gerar um id único para cada aluno justamente para mim poder aprender a usar uuid no java e explorar o pacote `java.util`.
