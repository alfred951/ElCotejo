<?php
defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Usuario extends REST_Controller {
    function __construct()
    {
        parent::__construct();
        $this->methods['Jugador_get']['limit'] = 500; // 500 requests per hour per user/key
        $this->methods['Amigos_get']['limit'] = 500; // 500 requests per hour per user/key
	    $this->load->model('Jugador_model');
    }
    function Jugador_get(){
        if(!$this->get('nickname')){
            $this->response(NULL, 400);
        }
        $Jugador = $this->Jugador_model->getjugador(strtolower($this->get('nickname')),
                                                    strtolower($this->get('nombre')),
                                                    strtolower($this->get('edad')),
                                                    strtolower($this->get('clave')),
                                                    strtolower($this->get('imagen')),
                                                    strtolower($this->get('bio')),
                                                    strtolower($this->get('correo')),
                                                    strtolower($this->get('posicion')));
        $this->response($Jugador, 200);
    }

    function Jugadores_get(){
        $Jugadores = $this->Jugador_model->getJugadores();
        $this->response($Jugadores, 200);
    }

    function Amigos_get(){
        if(!$this->get('nickname')){
            $this->response(NULL, 400);
        }
        $Amigos = $this->Jugador_model->getAmigos(strtolower($this->get('nickname')),
                                                   strtolower($this->get('amigo')));
        $this->response($Amigos, 200);
    }

    function Equipos_get(){
        if(!$this->get('nickname')){
            $this->response(NULL, 400);
        }
        $Equipos = $this->Jugador_model->getEquipos(strtolower($this->get('nickname')));
        $this->response($Equipos, 200);
    }

    function Partidos_get(){
        if(!$this->get('nickname')){
            $this->response(NULL, 400);
        }
        $Partidos = $this->Jugador_model->getPartidos(strtolower($this->get('nickname')));
        $this->response($Partidos, 200);
    }
}
