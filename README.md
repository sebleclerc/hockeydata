# HockeyData
## Easy tasks
To update all rosters + add missing players:  
`./run.sh cache teams && mysqldump hockeydata > assets/database.sql`

Pour mettre à jour TOUS les joueurs:  
`./run.sh cache player all && mysqldump hockeydata > assets/database.sql`

Pour avoir une idée de l'année en cours:  
`./run.sh pool me`

Questions salaire...  
`./run.sh salary all`  
`./run.sh salary team XX`

Et pour demander le salaire des joueurs manquants, on ajoute `--force`

Pool  
Pour enlever un joueur:  
`./run.sh pool taken XXXX`

Pour trouver tous les joueurs sélectionnés:
```
SELECT id, firstName, lastName, statut
FROM Players p 
RIGHT JOIN PoolDraft pd
ON p.id = pd.playerId
WHERE season = 20252026
AND statut != 1
ORDER BY lastName
```

Pour mettre à jour un joueur:
```
UPDATE PoolDraft
SET statut = 0
WHERE playerId = XXX
AND season = 20252026
```

## More informations
For a list of informations, we can visit: 
https://gitlab.com/dword4/nhlapi/-/blob/master/stats-api.md

Some generic API usage
https://medium.com/@vtashlikovich/nhl-api-what-data-is-exposed-and-how-to-analyse-it-with-python-745fcd6838c2

## Pool
### Règles

9 échanges  
Attaquants : Buts = 2pts, passe = 1 pts  
Défenseurs : But = 3 pts, passe = 1.5pts  
Gardien : Victoire = 3 pts, blanchissages = 3 pts, passe = 2 pts, but = 5 pts, fusillade = 1 pts pour défaite 2 pour victoire  
Un échange pour un joueur LTIR ne compte pas.

## Requête de vérification
SELECT id, firstName, lastName, psa.season, poolPoints, avv, poolPoints/(avv/10000) as Value FROM Players p, PlayersStatsArchive psa, PlayersSalaries sal WHERE p.id = psa.playerId AND p.id = sal.playerId AND psa.season = sal.season AND psa.leagueName = "NHL" ORDER BY Value DESC

## Roadmap
Importer le roster des differentes annees

Outil pour importer le salaire

Trouver l'évolution d'un joueur
Comparer les statistiques
dans la AHL, ECHL, CHL
et autres

### Generate PDF
Possiblement générer des fichiers PDFs de différents sujets
Voir ça:  
https://github.com/prawnpdf/prawn
