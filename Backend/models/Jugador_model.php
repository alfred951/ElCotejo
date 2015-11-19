<?php

class Jugador_model extends CI_Model {
    public function __construct(){
        parent::__construct();
    }

    public function getJugadores(){
        $query = $this->db->get('Jugador');
        return $query->result();
    }

    public function getjugador($nickname,$nombre,$edad,$clave,$imagen,$bio,$correo,$posicion){
        $data = array();
        if(!empty($nickname)){
            $nickname = urldecode($nickname);
            $data['nickname'] = $nickname;
        }
        if(!empty($nombre)){
            $nombre = urldecode($nombre);
            $data['nombre'] = $nombre;
        }
        if(!empty($edad)){
            $edad = urldecode($edad);
            $data['edad'] = $edad;
        }
        if(!empty($clave)){
            $clave = urldecode($clave);
            $data['clave'] = $clave;
        }
        if(!empty($imagen)){
            $imagen = urldecode($imagen);
            $data['imagen'] = $imagen;
        }
        if(!empty($bio)){
            $bio = urldecode($bio);
            $data['bio'] = $bio;
        }
        if(!empty($correo)){
            $correo = urldecode($correo);
            $data['correo'] = $correo;
        }
        if(!empty($posicion)){
            $posicion = urldecode($posicion);
            $data['posicion'] = $posicion;
        }
        if (!empty($data)){
            $this->db->select('*');
            $this->db->where('nickname',$nickname);
            $this->db->from('Jugador');
            $result = $this->db->get()->result_array();
            if(empty($result)){
                $this->db->set($data);
                $this->db->insert('Jugador');
            }
            else{
                $this->db->set($data);
                $this->db->where('nickname',$nickname);
                $this->db->update('Jugador', $data);
            }
        }
        $this->db->select('*');
        $this->db->where('nickname',$nickname);
        $this->db->from('Jugador');
        $result = $this->db->get()->result_array();
        return $result;
    }

    public function getAmigos($nickname,$amigo){
        $data = array();
        if(!empty($amigo)){
            $amigo = urldecode($amigo);
            $data['amigo'] = $amigo;
        }
        if (!empty($data)){
            $this->db->select('*');
            $this->db->where('nickname',$nickname);
            $this->db->where('amigo',$amigo);
            $this->db->from('Amigos');
            $result = $this->db->get()->result_array();
            $this->db->reset_query();
            if(empty($result)){
                $nickname = urldecode($nickname);
                $data['nickname'] = $nickname;
                $this->db->set($data);
                $this->db->insert('Amigos');
                $this->db->reset_query();
                $data['nickname'] = $amigo;
                $data['amigo'] = $nickname;
                $this->db->set($data);
                $this->db->insert('Amigos');
            }
        }
    $this->db->select('amigo');
    $this->db->from('Amigos');
    $this->db->where('nickname',$nickname);
    $query = $this->db->get();
    return $query->result();
    }

    public function getEquipos($nickname){
        $this->db->select('Equipo.idEquipo');
        $this->db->select('Equipo.nombre');
        $this->db->select('Equipo.capitan');
        $this->db->from('Integrantes');
        $this->db->join('Equipo', 'Integrantes.idEquipo = Equipo.idEquipo');
        $this->db->where('nickname',$nickname);
        $query = $this->db->get();
        return $query->result();
    }

    public function getPartidos($nickname){
        $this->db->select('Partido.*');
        $this->db->from('Participantes');
        $this->db->join('Partido', 'Participantes.idPartido = Partido.idPartido');
        $this->db->where('nickname',$nickname);
        $query = $this->db->get();
        return $query->result();
    }
}
?>
