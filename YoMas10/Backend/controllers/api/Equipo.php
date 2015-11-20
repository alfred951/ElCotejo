<?php
defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Equipo extends REST_Controller {
    function __construct()
    {
        parent::__construct();
        $this->methods['Equipo_get']['limit'] = 500; // 500 requests per hour per user/key
        $this->methods['Integrantes_get']['limit'] = 500; // 500 requests per hour per user/key
	    $this->load->model('Equipo_model');
    }
    function Equipo_get(){
        $Equipo = $this->Equipo_model->getEquipo(strtolower($this->get('capitan')),
                                                 strtolower($this->get('nombre')),
                                                 strtolower($this->get('idEquipo')));
        $this->response($Equipo, 200);
    }
    function Equipos_get(){
        $Partidos = $this->Equipo_model->getEquipos();
        $this->response($Partidos, 200);
    }
    function Integrantes_get(){
        if(!$this->get('idEquipo')){
            $this->response(NULL, 400);
        }
        $Integrantes = $this->Equipo_model->getIntegrantes(strtolower($this->get('idEquipo')),
                                                           strtolower($this->get('nickname')));
        $this->response($Integrantes, 200);
    }
}
