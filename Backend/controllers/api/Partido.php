<?php
defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Partido extends REST_Controller {
    function __construct()
    {
        parent::__construct();
        $this->methods['Partido_get']['limit'] = 500; // 500 requests per hour per user/key
        $this->methods['Participantes_get']['limit'] = 500; // 500 requests per hour per user/key
	    $this->load->model('Partido_model');
    }
    function Partido_get(){
        $Partido = $this->Partido_model->getPartido(strtolower($this->get('idPartido')),
                                                    strtolower($this->get('cancha')),
                                                    strtolower($this->get('hora')),
                                                    strtolower($this->get('fecha')));
        $this->response($Partido, 200);
    }
    function Partidos_get(){
        $Partidos = $this->Partido_model->getPartidos();
        $this->response($Partidos, 200);
    }
    function Participantes_get(){
        $Participantes = $this->Partido_model->getParticipantes(strtolower($this->get('idPartido')),
                                                         strtolower($this->get('idEquipo1')),
                                                         strtolower($this->get('idEquipo2')),
                                                         strtolower($this->get('nickname')));
        $this->response($Participantes, 200);
    }
}
