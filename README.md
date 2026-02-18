# PolyHome - Application Domotique Android

Application mobile Android permettant de piloter à distance les équipements connectés d'une maison (volets, garages, lumières) via une API REST. Développée dans le cadre du cours de Programmation Mobile Android - Polytech Dijon.

---

## 🏗️ Architecture

```
app/src/main/java/com/example/projet1/
├── data/              # Couche de données (modèles, réseau, repositories)
│   ├── models/        # Classes de données pour représenter les entités (Device, User, House, Command)
│   ├── network/       # Gestion des appels HTTP vers l'API
│   └── repository/    # Couche d'abstraction pour l'accès aux données (appels API)
├── ui/                # Couche de présentation (Activities, Adapters)
│   ├── auth/          
│   ├── houses/        
│   ├── devices/       
│   └── user/          
└── utils/             # Classes utilitaires et constantes de l'application
```

---

## ✅ Fonctionnalités de base

- Création de compte utilisateur
- Connexion avec mémorisation du token (SharedPreferences)
- Liste des maisons accessibles
- Liste des périphériques d'une maison
- Détail d'un périphérique
- Envoi de commandes aux périphériques (OPEN, CLOSE, STOP, TURN ON, TURN OFF)

---

## 🎁 Fonctionnalités bonus

- **Mode Nuit** : Bouton pour fermer tous les volets/garages et éteindre toutes les lumières en un clic
- **Filtres par catégorie** : Filtrage des devices (Tous, Volets, Garages, Lumières)
- **Gestion des utilisateurs** : Invitation et suppression d'utilisateurs (propriétaire uniquement)
- **Contrôle d'accès basé sur les rôles** : Users d'une maison visible uniquement pour les propriétaires
- **Auto-complétion** : Suggestions pour la recherche de maisons et l'invitation d'utilisateurs
- **Recherche de maisons** : Barre de recherche avec filtrage en temps réel


---

## 👥 Auteurs

Projet réalisé par NGAH NDONGO Estelle Clotilde - 4A ILIA Polytech Dijon
